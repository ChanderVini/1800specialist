var nbrOfRows = 0;

function handleSubmit (actionname) {
    document.forms[0].action = actionname;
}

function toggleChildRow(arr,rowID,childs){
    for (i=0;i<childs;i++){	
        child = document.getElementById(rowID+i);
        if (child.style.display == 'none'){
            child.style.display = 'block';
            arr.src = 'images/arrow_collapse.gif';
        }else{
            child.style.display = 'none';
            arr.src = 'images/arrow_expand.gif';
        }
    }	
}