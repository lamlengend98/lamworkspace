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

addStudent(4, 'Lam');
addStudent(1, 'Ngoc');
addStudent(2, 'Thanh');
addStudent(3, 'Van');


showAll(1);
