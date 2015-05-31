var express = require('express');
var bodyParser = require('body-parser');
var multer = require('multer');
var fs = require('fs');

var app = module.exports = express();
var configFileName = "./config.json";

app.use('/static', express.static('helper_static'));
app.use(bodyParser.json()); // for parsing application/json
app.use(bodyParser.urlencoded({ extended: true })); // for parsing application/x-www-form-urlencoded
app.use(multer()); // for parsing multipart/form-data

app.get('/', function(request, response) {
	response.redirect('/static/index.html');
});

app.get('/config', function(request, response) {
	response.json(readDemoConfig());
});

app.post('/config', function(request, response) {
	var data = request.body;
	console.log(data);
	var demoConfig = readDemoConfig();
	for (var key in data) {
		if (data.hasOwnProperty(key)) {
			console.log("Adding " + key);
			demoConfig[key] = data[key];
		}
	}
	console.log(demoConfig);
	fs.writeFile(configFileName, JSON.stringify(demoConfig), function(err){
		if (err) return console.log(err);
		console.log("Saved " + configFileName);
	});
	response.sendStatus(200);
});

function readDemoConfig() {
	try {
		return JSON.parse(fs.readFileSync(configFileName, 'utf-8'));
	} catch (e) {
		return {};
	}
}
