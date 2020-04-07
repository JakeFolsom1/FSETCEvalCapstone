const tutorsList = [
    "Jake",
    "Joe",
    "John",
    "Names with J"
];


$(document).ready(() => {
    var url = document.location.href,
    params = url.split('?')[1].split('&'),
    data = {}, tmp;
    for (var i = 0, l = params.length; i < 1; i++) {
        tmp = params[i].split('=');
        data[tmp[0]] = tmp[1];
    }
    document.getElementById('modifyHeader').innerHTML = data.name + " shared this evaluation with you";
});

//Is running better as a function
$(function () {
    $('#slider').on('input change', function(){
        $(this).next($('#slider-label')).html(this.value);
    });
});

function submitAndReturnToPage(){
    document.location.href = "http://localhost:63342/FSETCEvalCapstone/src/evaluations.html";
}

$.each(tutorsList, function(i, item){
    var newListItem = document.createElement("option");
    newListItem.className = 'ui-state-default';
    var itemText = document.createTextNode(item[1]);
    newListItem.appendChild(itemText);
});