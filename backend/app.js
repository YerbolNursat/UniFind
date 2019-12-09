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
        "Стабильность работы": 0,
        "Стабильность места жительства": 0,
        "Служение": 0,
        "Вызов": 0,
        "Интеграция стилей жизни": 0,
        "Предпринимательство": 0,
    };
    for (i = 1; i < arr.length + 1; i++) {
        if ([1, 2, 17, 25, 33].includes(i)) {
            result["Профессиональная компетентность"] = result["Профессиональная компетентность"].valueOf() + parseInt(arr[i - 1]);
        } else if ([3, 11, 19, 27, 35].includes(i)) {
            result["Автономия"] = result["Автономия"].valueOf() + parseInt(arr[i - 1]);

        } else if ([4, 12, 36].includes(i)) {
            result["Стабильность работы"] = result["Стабильность работы"].valueOf() + parseInt(arr[i - 1]);

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
            result["Стабильность места жительства"] = result["Стабильность места жительства"].valueOf() + parseInt(arr[i - 1]);
        }
        if ([41].includes(i)) {
            for (let key in result) {
                if (key === "Стабильность работы" || key === "Стабильность места жительства") {
                    result[key] = result[key] / 3
                } else {
                    result[key] = result[key] / 5
                }
                result[key] = result[key] * 100 / 10
                if (result[key] < 50) {
                    delete result[key]
                }
            }
        }
    }
    return result;
}


async function setData(result, data) {
    for (let position in result) {
        for (let key in result[position]) {
            for (let key1 in data) {
                if (result[position].Name.valueOf()===key1){
                    result[position].Score=data[key1].valueOf()
                }
            }
        }
    }
    return result
}

app.get('/get/:data', (req, res) => {
    let data = req.params.data.split(",");
    getData(data).then(data => connection.query('Select * from Direction where name in (' + sql.escape((Object.keys(data))) + ');',
        (err, result) => {
            if (err) throw err
            setData(result, data).then(result => res.send(result))
        }))
})

app.get('/', (req, res) => {
    res.redirect('/universities')
})
app.get('/universities', (req, res) => {
    var query = 'Select id,Name,Code,City,Dormitory from Universities';
    connection.query(
        query, (error, result) => {
            if (error) throw(error);
            res.send(result);
        }
    );
});
app.get('/university/:id', (req, res) => {
    var query = "Select * from `Universities` where id=" + sql.escape(req.params.id);
    connection.query(
        query, (error, result) => {
            if (error) throw(error);
            res.send(result);
        }
    );
});

app.get('/direction/:name', (req, res) => {
    var query = "select Professions.name from Direction inner join Specialities on(Direction.Id=Specialities.Direction_id)\n" +
        "inner join SpeToPro on (SpeToPro.Specialities_id=Specialities.id)\n" +
        "inner join Professions on (Professions.id=SpeToPro.Professions_id) where Direction.name=" + sql.escape(req.params.name) + "group by Professions.name ";
    connection.query(
        query, (error, result) => {
            if (error) throw(error);
            res.send(result);
        }
    );
});
app.get('/professions/:direction',(req,res)=>{
    var data = req.params.direction;
    var query ="select Professions.Name from Direction inner join Specialities on Direction.Id=Specialities.Direction_Id inner join SpeToPro on Specialities.id=SpeToPro.Specialities_id inner join Professions on SpeToPro.Professions_id=Professions.id where Direction.Name ="+sql.escape(data)+"group by Professions.name;";
    connection.query(
        query, (error, result) => {

            if (error) throw(error);
            res.send(result);
        }
    );
})
app.get('/specialities/:parameters', (req, res) => {
    var data = req.params.parameters.split(",")
    var query = "select univer.Name as Uni_name, s.Name as Spec_name,s.Code, s.ENT ,p.Price from Prices p, SpeToPro t1, Universities univer ,Specialities s, Professions pr where univer.id=p.Universities_id and s.id= p.Specialities_id and s.id=t1.Professions_id and pr.id= t1.Professions_id and pr.name  in (" + sql.escape(data) + ")";
    connection.query(
        query, (error, result) => {

            if (error) throw(error);
            res.send(result);
        }
    );
});

app.listen(9000);

