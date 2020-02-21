/*
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}
*/
//EvaluationList should be Unique for every person.
//Organized by Semester, Name, IsLead?. Link to the evaluation website as well though this will probably be phased out
const tutorsList = [
    ["Fall 2019", "Bob", false, "http://www.google.com"],
    ["Fall 2019", "Jake", true, "http://www.google.com"],
    ["Fall 2019", "John", false, "http://www.google.com"],
    ["Fall 2019", "Carmen", false, "http://www.yahoo.com"]
];

const evaluationsToComplete = [
    ["Bob", false],
    ["John", true],
    ["Jack", false],
    ["Mark", true]
];

$(document).ready(() => {
    //Setting up the draggable interface
    $('#sortable').sortable();
    $('#sortable').disableSelection();

    //Creates the elements for the draggable interface
    var frag = document.createDocumentFragment();
    $.each(evaluationsToComplete, function(i, item){
       var newListItem = document.createElement("li");
       newListItem.className = 'ui-state-default';
       var itemText = document.createTextNode(item[0]);
       newListItem.appendChild(itemText);
       frag.append(newListItem);
    });

    $('#sortable')[0].appendChild(frag);

    $('#viewSharedTable').DataTable({
        paging : false,
        info : false,
        searching : false,
        data : tutorsList,
        columns : [
            {
                title : "Semester",
            },
            {
                title : "Name",
            },
            {
                title : "Lead",
                render : data => {
                    return data ? '<div class="dot"/>' : "";
                }
            },
            {
                title : "Actions",
                render : function(data, type, row){
                     return `<button class="btn-primary" onclick="openPage()">View</button>`
                }
            }
        ]
    });
    $("#sortable").sortable();
    $("#sortable").disableSelection();
    $('#completeAssignmentsTable').DataTable({
        paging: false,
        info: false,
        searching: false,
        data: evaluationsToComplete,
        columns : [
            {
                title: "Tutor Name",
            },
            {
                title : "Lead",
                render : data => {
                    return data ? '<div class="dot"/>' : "";
                }
            },
            {
              title: "Actions",
              render : function(data, type, row){
                  return '<button class="btn-primary" onclick="openEvaluationPage()">Evaluate</button>'
              }
            }
        ]
    });
});

function openPage(){
    return `<button class="btn-primary">Page goes here</button>`
}

function openEvaluationPage(){
    return '<button class="btn-primary">Page goes here</button>'
}



function generateTable(document){
    //Foreach element in API call return, create a new <tr> element as well as <th> for every <tr>
}
