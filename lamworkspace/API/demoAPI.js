$(document).ready(function(){

	function clear() {
		const panels = ['div#post','div#get','div#put','div#delete'];
		panels.forEach(function(item) {
			$(item).addClass("disable");			
		})
		
	}
	function showPanel(id) {
		clear();
		$(id).removeClass("disable");
	}

	// GET
	function get_data(){
		showPanel('div#get');
		$.get( 
			"http://127.0.0.1:8888/student",function( data ) {
				var student = "<tr><th>Id</th><th>Name</th><th>Age</th></tr>";
				for (var i = 0; i < data.length; i++) {
					student += '<tr><td>'+data[i].id+'</td><td>'+data[i].name+'</td><td>'+data[i].age+'</td><tr>'
				}
				$('table#get-table').html(student);
			});
	}

	$("#get").click(function(){
		get_data();
	});

	$("#post").click(function(){
		post_data();

	});


	function validForm(formData) {
		if (formData.name == "") {
			$("p#warning-name").removeClass("disable");
			$("input#post-name").css({
				"border":"1px solid red"
			});
			if(formData.age == ""){
				$("p#warning-age").removeClass("disable");
				$("input#post-age").css({
					"border":"1px solid red"
				});
			}
			return false;
		}


		if (!$.isNumeric(formData.age)) {
			alert("Age is a number!!!");
			return false;
		}
	}


	// POST
	function post_data(){
		showPanel('div#post');
		$("#submit").click(()=>{

			var data = {
				name : $("#post-name").val(), 
				age: $("#post-age").val()
			};

			if (!validForm(data)) return false;

			$.ajax({
				method: "POST",
				url: "http://127.0.0.1:8888/student",
				data:JSON.stringify(data),
				contentType: 'application/json',
				success: function(){
					get_data();
					alert("Add successfully");
					$("p#warning-age, p#warning-name").addClass("disable");
					$("input#post-age, input#post-name").css({
						"border":"1px solid #ced4da"
					});
				}

			}).done(function( msg ) {
				var student = JSON.stringify(msg);
				Lam261198
			}).fail((err)=>{
				console.log(err);
			});
		});
	}
	

	// PUT
	$("#put").click(function(){
		showPanel('div#put');
		$("#update").click(function(){
			//validate
			var id = $('#put-id')[0].value;
			var name = $('#put-name')[0].value;
			var age = $('#put-age')[0].value;
			var data = {id: id,
				name : name,
				age : age}
				if($.isNumeric(id) == false || id == ""){
					$('p#warning-put-id').removeClass('disable');
					$('input#put-id').css({
						"border":"1px solid red"
					});

				}
				if(name == ""){
					$('p#warning-put-name').removeClass('disable');
					$('input#put-name').css({
						"border":"1px solid red"
					});
				}
				if($.isNumeric(age) == false || id == ""){
					$('p#warning-put-age').removeClass('disable');
					$('input#put-age').css({
						"border":"1px solid red"
					});
					return false;
				}
				// alert($.isNumeric(age));


			//api
			/*let checkID = (id)=> {
				var student;
					$.get( 
						"http://127.0.0.1:8888/student",function( data ) {
							for (var i = 0; i < data.length; i++) {
								student = data[i].id;
								if(id == student){
									return true;
								}
							}
						}
					);
				}*/

				$.ajax({
					method: "PUT",
					url: "http://127.0.0.1:8888/student/"+id,
					data: JSON.stringify(data),
					contentType: 'application/JSON',

				}).done(async (result)=>{
				// console.log(result);
				// console.log(checkID(id));
				var student;
				var flag = false;
				await $.get( 
					"http://127.0.0.1:8888/student",function( data ) {
						for (var i = 0; i < data.length; i++) {
							console.log(result.id);
							student = data[i].id;
							if(result.id == student){
								flag = true;
								break;
							}

						}
					}
					);
				// console.log(flag);
				if(flag){
					alert("Update successfully");
					get_data();
					$('p#warning-put-id, p#warning-put-name, p#warning-put-age').addClass('disable');
					$('input#put-id, input#put-name, input#put-age').css({
						"border":"1px solid #ced4da"
					});
				}
				else {
					alert("ID not exist");
				}
			})
			});
	});

	// DELETE
	$("#delete").click(function(){
		showPanel('div#delete');
		$("input#delete").click(function(){
			
			// check empty
			var data = $('#delete-id').val();
			if($.isNumeric(data) == false || data == ""){
				$("p#warning-delete-id").removeClass("disable");
				$("input#delete-id").css({
					"border":"1px solid red"
				});
				return false;
			}
			
			//
			$.ajax({
				method: "DELETE",
				url: "http://127.0.0.1:8888/student/"+data,
				success: function(){
					alert("Delete successfully");
					get_data();
					$("p#warning-delete-id").addClass("disable");
					$("input#delete-id").css({
						"border":"1px solid #ced4da"
					});
					
				},
				error: function(){
					alert("Invailable ID\nPlease insert again!!!");
					return false;
				}
				/*$('table#get-table').html(student);*/
				
			});


		})
		
	});

});