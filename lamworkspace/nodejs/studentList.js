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
	for(let i = 0; i < students.length; i++){
		console.log('Student: ' + students[i].fullname + "(" +students[i].id+ ")");
	}
}

function showAll(id){
	var students = getAll();
	for (var i = 0; i < students.length; i++) {
		
		if (students[i].id == id) {
			console.log(i);
			console.log(students[i]);
			console.log('Student: '+students[i].fullname+'\nID: '+students[i].id);
			
		}
		
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

getAll();
