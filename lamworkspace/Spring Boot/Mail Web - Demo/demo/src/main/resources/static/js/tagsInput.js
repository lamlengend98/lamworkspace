function onAddTag(tag) {
    alert("Added a tag: " + tag);
}
function onRemoveTag(tag) {
    alert("Removed a tag: " + tag);
}

function onChangeTag(input,tag) {
    alert("Changed a tag: " + tag);
}

$(function() {
    var mailList = [];
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