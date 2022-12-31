function MaxTable(){
	var table = document.getElementById("kbtable");
	var body = document.getElementsByTagName("body")[0];
	body.innerHTML="";
	body.appendChild(table);
	return table;
}
MaxTable();