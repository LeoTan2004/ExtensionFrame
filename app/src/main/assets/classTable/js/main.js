function MaxTable(){
	var table = document.getElementById("kbtable");
	alert("1122");
	if(table!=null){
	    var body = document.getElementsByTagName("body")[0];
	    body.innerHTML="";
	    body.appendChild(table);
	    return table;
	}
}
MaxTable();