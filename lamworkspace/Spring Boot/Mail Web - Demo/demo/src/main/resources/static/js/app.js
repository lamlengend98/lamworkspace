(function () {
    function onAddTag(tag) {
        alert("Added a mail: " + tag);
    }
    function onRemoveTag(tag) {
        alert("Removed a mail: " + tag);
    }

    function onChangeTag(input,tag) {
        alert("Changed a mail: " + tag);
    }

    var mailList = [];

    $(function() {

        var regex4 = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;// Email address

        $('.tags').tagsInput({
            onChange: function(elem, elem_tags)
            {
                mailList.push(elem_tags);
                console.log(mailList)
            },
            width: 'auto',
            pattern: regex4
        });

    });
    "use strict";
    var attachmentDropzone;
    var attachmentDropzoneId ="#attachment-dropzone-tab";
    $(function () {
        initDropzone();
        f();
    })
    var data1 = [];
    function initDropzone() {
        Dropzone.autoDiscover = false;
        attachmentDropzone = new Dropzone("div" + attachmentDropzoneId, {
            url: "/toEmail/upload",
            addRemoveLinks: true,
            maxFilesize: 2,
            filesizeBase: 1000,
            dictRemoveFile: "Delete",
            dictCancelUpload: "キャンセル",
            dictDefaultMessage: "Drop files here or click to upload.",
            init: function() {
                this.on("success", function(file, response) {
                    console.log(response)
                    if(response && response.status){
                        var data = response.list && response.list.length > 0 ? response.list[0] : null;
                        data1.push(data)
                        if(data){
                            // console.log(data)
                        }

                    }
                });
                this.on("removedfile", function(file) {
                    if(!!file && !!file.upload && !!file.id){
                        removeFile(file.id)
                        console.log(file.id)
                    }
                });
            },
            thumbnail: function(file, dataUrl) {}
        })
    }

    function f() {
        $("button#submitButton").click(function () {
            console.log(data1)
            var form = {
                subject: $("#title").val(),
                receiver: mailList,
                content: tinyMCE.get('texteditor').getContent(),
                attachment: data1
            }
            console.log(form.receiver)
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/toEmail/save",
                data: JSON.stringify(form),
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log(data)
                    if(data && data.status){
                        console.log("success")

                    } else {
                        console.log("error")
                    }
                    window.location.replace("/toEmail")

                },
                error: function (e) {
                    console.error("ERROR : sendSuggestMail: ", e);
                    console.log("error")
                }
            });
        })
    }




})(jQuery);
