const express= require('express');
const app = express();
const sql = require('mysql');
const connection = sql.createPool({
    connectionLimit : 10,
    host: 'ec2-35-177-227-81.eu-west-2.compute.amazonaws.com',
    port:8080,
    user: 'user',
    password: 'user',
    database: 'Universities'
});
function removeEmpty(obj) {
    Object.keys(obj).forEach(function(key) {
        (obj[key] === '' || obj[key] === null) && delete obj[key]
    });
    return obj;

};
app.get('/',(req,res)=>{
    connection
    res.redirect('/universities')
})
app.get('/universities', (req, res) => {
    var query= 'Select * from Universities';
    connection.query(
        query, (error,result)=>{
            if(error) console.log(error);
            res.send(result);
        }
    );
});
app.get('/university/:id', (req, res) => {
    var query= "Select * from `Universities` where id="+sql.escape(req.params.id);
    connection.query(
        query, (error,result)=>{
            if(error) console.log(error);
            res.send(result);
        }
    );
});
app.get('/direction/:name', (req, res) => {
    var query= "select Professions.name from Direction inner join Specialities on(Direction.Id=Specialities.Direction_id)\n" +
        "inner join SpeToPro on (SpeToPro.Specialities_id=Specialities.id)\n" +
        "inner join Professions on (Professions.id=SpeToPro.Professions_id) where Direction.name="+sql.escape(req.params.name)+"group by Professions.name ";
    connection.query(
        query, (error,result)=>{
            if(error) console.log(error);
            res.send(result);
        }
    );
});
app.get('/professions/:parameters', (req, res) => {
    var data=req.params.parameters.split(",")
    var query= "select univer.Name as Uni_name, s.Name as Spec_name,s.Code, s.ENT ,p.Price from Prices p, SpeToPro t1, Universities univer ,Specialities s, Professions pr where univer.id=p.Universities_id and s.id= p.Specialities_id and s.id=t1.Professions_id and pr.id= t1.Professions_id and pr.name  in ("+sql.escape(data)+")";
    connection.query(
        query, (error,result)=>{
            if(error) console.log(error);
            res.send(result);
        }
    );
});
app.listen(9000);