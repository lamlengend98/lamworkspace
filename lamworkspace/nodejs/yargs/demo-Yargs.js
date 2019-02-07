var storage = require('node-persist');

var students = {
	id : "",
	fullname: ""
};

storage.initSync({
	dir: "Student List",
	ttl: false
});

function getAll(){
	var students = storage.getItemSync('Student List');
	if (typeof students === "undefined") {
		return [];
	}
	return students;
}

function showAll(){
	var students = getAll();
	for (var i = 0; i < students.length; i++) {
		console.log('Student: '+students[i].fullname+'<ID: '+students[i].id+">");
	}
}

function addStudent(studentID, studentName){
	var students = getAll();
	
	students.push({
		id: studentID,
		fullname: studentName
	});

	storage.setItemSync('Student List', students);
	
}

function removeStudent(studentID){
	var students = getAll();

	for(let i = 0; i < students.length; i++){
		if(students[i].id === studentID){
			students.splice(i,1);
		}
	}

	storage.setItemSync('Student List', students);
	
}

function updateStudent(studentID, studentName){
	var students = getAll();
	for(let i = 0, length1 = students.length; i < length1; i++){
		if(students[i].id === studentID){
			students[i].fullname = studentName;
		}
	}
	storage.setItemSync('Student List', students);
	
}

var yargs = require('yargs');

var argv = yargs
.command("list", "Get List Student", function(yargs){

})
.command('create', 'Create a Student', function(yargs){
	return yargs.options({
		id: {
			demand: true,
			type: "number"
		},
		fullname: {
			demand: true,
			type: "string"
		}
	});

})
.command('update','Update a Student', function(yargs){
	return yargs.options({
		id: {
			demand: true,
			type: "number"
		},
		fullname: {
			demand: true,
			type: "string"
		}
	});
})
.command('delete','Delete a Student', function(yargs){
	return yargs.options({
		id: {
			demand: true,
			type: "number"
		}
	});
})
.argv;
var action = argv._[0];
if(action === 'list'){
	showAll();
	console.log('Show list successfully');
} else if(action === 'create'){
	addStudent(argv.id, argv.fullname);
	showAll();
	console.log('Create a student successfully');
} else if(action === 'update'){
	updateStudent(argv.id,argv.fullname);
	showAll();
	console.log('Update a student successfully');
} else if(action === 'delete'){
	removeStudent(argv.id);
	showAll();
	console.log('Delete a student successfully');
}