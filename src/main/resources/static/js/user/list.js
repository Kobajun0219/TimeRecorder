	//出勤退勤ボタンの制御
	function startFunc() {
		if(checkRecord ==0){
			document.getElementById("finish").classList.add("disabled");
		}else{
			document.getElementById("start").classList.add("disabled");
			alert("出勤中");
		}
	}