const express = require('express');
const app = express();

// var oracledb = require('oracledb');
// oracledb.getConnection(
//     {
//         user: "admin",
//         password: "positive21",
//         connectString: "(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = oracledatabase.cctfa6ltiyeg.eu-west-2.rds.amazonaws.com)(PORT = 1521))(CONNECT_DATA =(SID= ORCL)))"
//     },
//     function (err, connection) {
//         if (err) {
//             console.error(err);
//             return;
//         }
//         connection.execute(
//             "SELECT * "
//             + "FROM universities "
//             , function (err, result) {
//                 if (err) {
//                     console.error(err);
//                     return;
//                 }
//                 console.log(result.rows);
//             });
//     });

const sql = require('mysql');
const connection = sql.createPool({
    connectionLimit: 10,
    host: 'ec2-35-176-24-130.eu-west-2.compute.amazonaws.com',
    port: 8080,
    user: 'user',
    password: 'user',
    database: 'mydb'
});


// function removeEmpty(obj) {
//     Object.keys(obj).forEach(function (key) {
//         (obj[key] === '' || obj[key] === null) && delete obj[key]
//     });
//     return obj;
// };

async function getData(arr) {
    let result = {
        "Профессиональная компетентность": 0,
        "Менеджмент": 0,
        "Автономия": 0,
        "Стабильность": 0,
        "Служение": 0,
        "Вызов": 0,
        "Интеграция стилей жизни": 0,
        "Предпринимательство": 0
    };
    for (i = 1; i < arr.length + 1; i++) {
        if ([1, 2, 17, 25, 33].includes(i)) {
            result["Профессиональная компетентность"] = result["Профессиональная компетентность"].valueOf() + parseInt(arr[i - 1]);
        } else if ([3, 11, 19, 27, 35].includes(i)) {
            result["Автономия"] = result["Автономия"].valueOf() + parseInt(arr[i - 1]);

        } else if ([4, 12, 36].includes(i)) {
            result["Стабильность"] = result["Стабильность"].valueOf() + parseInt(arr[i - 1]);

        } else if ([5, 13, 21, 29, 37].includes(i)) {
            result["Служение"] = result["Служение"].valueOf() + parseInt(arr[i - 1]);

        } else if ([6, 14, 22, 30, 38].includes(i)) {
            result["Вызов"] = result["Вызов"].valueOf() + parseInt(arr[i - 1]);

        } else if ([7, 15, 23, 31, 39].includes(i)) {
            result["Интеграция стилей жизни"] = result["Интеграция стилей жизни"].valueOf() + parseInt(arr[i - 1]);

        } else if ([8, 16, 24, 32, 40].includes(i)) {
            result["Предпринимательство"] = result["Предпринимательство"].valueOf() + parseInt(arr[i - 1]);

        }
        if ([2, 10, 18, 26, 34].includes(i)) {
            result["Менеджмент"] = result["Менеджмент"].valueOf() + parseInt(arr[i - 1]);
        }
        if ([20, 28, 41].includes(i)) {
            result["Стабильность"] = result["Стабильность"].valueOf() + parseInt(arr[i - 1]);
        }
        if ([41].includes(i)) {
            for (let key in result) {
                if (key === "Стабильность") {
                    result[key] = result[key] / 6
                } else {
                    result[key] = result[key] / 5
                }
                result[key] = Math.floor(result[key] * 100 / 10);

                if (result[key] < 50) {
                    delete result[key]
                }
            }
        }
    }
    return result;
}


async function setData(result, data, lang) {

    for (let position in result) {

        for (let key1 in data) {
            if (result[position].Name.valueOf().trim() === key1) {
                result[position].Score = data[key1].valueOf()
            }
        }

    }
    for (let position in result) {
        for (let key in result[position]) {
            if (lang === "ENG") {
                result[position].Name = result[position].Name_ENG.valueOf()
                result[position].Description = result[position].Description_ENG.valueOf()

            } else if (lang === "KZ") {
                result[position].Name = result[position].Name_KZ.valueOf()
                result[position].Description = result[position].Description_KZ.valueOf()

            }
        }
    }
    for (let position in result) {
        for (let key in result[position]) {
            delete (result[position].Name_ENG)
            delete (result[position].Description_ENG)
            delete (result[position].Name_KZ)
            delete (result[position].Description_KZ)
        }
    }


    return result
}

app.get('/get/:data/:lang', (req, res) => {
    let data = req.params.data.split(",");
    let lang = req.params.lang;
    getData(data).then(data => connection.query('Select * from Direction where name in (' + sql.escape((Object.keys(data))) + ');',
        (err, result) => {
            if (err) throw err
            setData(result, data, lang).then(result => {
                res.send(result)
            })
        }))
})

app.get('/', (req, res) => {
    res.redirect('/universities' + "/RU")
})
app.get('/universities/:lang', (req, res) => {
    let lang = req.params.lang;
    if (lang === "RU") {
        let query = 'Select id,Name,Code,City,Dormitory from Universities';
        connection.query(
            query, (error, result) => {
                if (error) throw(error);
                res.send(result);
            }
        );
    } else if (lang === "ENG") {
        let query = 'Select id,Name_ENG as Name,Code ,City_ENG as City,Dormitory from Universities';
        connection.query(
            query, (error, result) => {
                if (error) throw(error);
                res.send(result);
            }
        );
    } else if (lang === "KZ") {
        let query = 'Select id,Name_KZ as Name ,Code,City_KZ as City,Dormitory from Universities';
        connection.query(
            query, (error, result) => {
                if (error) throw(error);
                res.send(result);
            }
        );
    }
});
app.get('/university/:id', (req, res) => {
    let query = "Select * from `Universities` where id=" + sql.escape(req.params.id);
    connection.query(
        query, (error, result) => {
            if (error) throw(error);
            res.send(result);
        }
    );
});

app.get('/professions/:direction/:subject1/:subject2/:lang', (req, res) => {
    let data = req.params.direction;
    let subject1 = req.params.subject1;
    let subject2 = req.params.subject2;
    let lang = req.params.lang;
    if (lang === "RU") {
        let query = "select Professions.Name from Direction inner join Specialities on Direction.Id=Specialities.Direction_Id \n" +
            "inner join SpeToPro on Specialities.id=SpeToPro.Specialities_id inner join Professions on SpeToPro.Professions_id=Professions.id \n" +
            "where Direction.Name =" + sql.escape(data) + " and (Specialities.Subject1=" + sql.escape(subject1) + " or  Specialities.Subject1=" + sql.escape(subject2) + ") \n" +
            " and (Specialities.Subject2=" + sql.escape(subject1) + " or  Specialities.Subject2=" + sql.escape(subject2) + ") group by Professions.name;";
        connection.query(
            query, (error, result) => {
                if (error) throw(error);
                res.send(result);
            }
        );
    } else if (lang === "ENG") {
        let query = "select Professions.Name_ENG as Name  from Direction inner join Specialities on Direction.Id=Specialities.Direction_Id \n" +
            "inner join SpeToPro on Specialities.id=SpeToPro.Specialities_id inner join Professions on SpeToPro.Professions_id=Professions.id \n" +
            "where Direction.Name_ENG =" + sql.escape(data) + " and (Specialities.Subject1_ENG=" + sql.escape(subject1) + " or  Specialities.Subject1_ENG=" + sql.escape(subject2) + ") \n" +
            " and (Specialities.Subject2_ENG=" + sql.escape(subject1) + " or  Specialities.Subject2_ENG=" + sql.escape(subject2) + ") group by Professions.name;";
        connection.query(
            query, (error, result) => {
                if (error) throw(error);
                res.send(result);
            }
        );
    } else if (lang === "KZ") {
        let query = "select Professions.Name_KZ as Name from Direction inner join Specialities on Direction.Id=Specialities.Direction_Id \n" +
            "inner join SpeToPro on Specialities.id=SpeToPro.Specialities_id inner join Professions on SpeToPro.Professions_id=Professions.id \n" +
            "where Direction.Name_KZ =" + sql.escape(data) + " and (Specialities.Subject1_KZ=" + sql.escape(subject1) + " or  Specialities.Subject1_KZ=" + sql.escape(subject2) + ") \n" +
            " and (Specialities.Subject2_KZ=" + sql.escape(subject1) + " or  Specialities.Subject2_KZ=" + sql.escape(subject2) + ") group by Professions.name;";
        connection.query(
            query, (error, result) => {
                if (error) throw(error);
                res.send(result);
            }
        );
    }
})
app.get('/specialities/:parameters/:city/:lang', (req, res) => {
    let lang = req.params.lang;
    let data = req.params.parameters.split(",")
    let city = req.params.city

    if (lang === "RU") {
        let query = "select  DISTINCT Universities.Name as Uni_name, Specialities.Name as Spec_name,Specialities.Code, Specialities.ENT ,Prices.Price  from Universities \n" +
            "inner join Prices on Universities.id=Prices.Universities_id inner join Specialities on Specialities.id=Prices.Specialities_id \n" +
            "inner join SpeToPro on SpeToPro.Specialities_id=Specialities.id inner join Professions on Professions.id=SpeToPro.Professions_id where Professions.Name in (" + sql.escape(data) + ") \n" +
            " and Universities.City=" + sql.escape(city) + " ;";
        connection.query(
            query, (error, result) => {
                if (error) throw(error);
                res.send(result);
            }
        );
    } else if (lang === "ENG") {
        let query = "select  DISTINCT Universities.Name_ENG as Uni_name, Specialities.Name_ENG as Spec_name,Specialities.Code, Specialities.ENT ,Prices.Price  from Universities \n" +
            "inner join Prices on Universities.id=Prices.Universities_id inner join Specialities on Specialities.id=Prices.Specialities_id \n" +
            "inner join SpeToPro on SpeToPro.Specialities_id=Specialities.id inner join Professions on Professions.id=SpeToPro.Professions_id where Professions.Name_ENG in (" + sql.escape(data) + ") \n" +
            " and Universities.City_ENG=" + sql.escape(city) + " ;";
        connection.query(
            query, (error, result) => {
                if (error) throw(error);
                res.send(result);
            }
        );
    } else if (lang === "KZ") {
        let query = "select  DISTINCT Universities.Name_KZ as Uni_name, Specialities.Name_KZ as Spec_name,Specialities.Code, Specialities.ENT ,Prices.Price  from Universities \n" +
            "inner join Prices on Universities.id=Prices.Universities_id inner join Specialities on Specialities.id=Prices.Specialities_id \n" +
            "inner join SpeToPro on SpeToPro.Specialities_id=Specialities.id inner join Professions on Professions.id=SpeToPro.Professions_id where Professions.Name_KZ in (" + sql.escape(data) + ") \n" +
            " and Universities.City_KZ=" + sql.escape(city) + " ;";
        connection.query(
            query, (error, result) => {
                if (error) throw(error);
                res.send(result);
            }
        );
    }

});
app.listen(9000);
