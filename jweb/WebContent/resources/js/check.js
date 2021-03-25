/**
 *  폼 유효성 검사
  1.아이디 - 4자~12자 가능
  2.비밀번호 - 6자~15자까지 영문자, 숫자 조합(혼합) : 정규표현식
  3.비밀번호가 비밀번호 확인과 같지 않으면 '비밀번호를 동일하게 입력해 주세요'
  4.이름을 입력하지 않으면 '이름을 입력해 주세요'
 */


function checkMember(){
	var form = document.regForm;
    var id = form.memberId.value;
	var pw1 = form.passwd.value;
	var pw2 = form.passwd_confirm.value;
	var name = form.name.value;
	
	var regExPw = /^[a-zA-Z0-9]{6,15}$/;
	var chk_num = pw1.search(/[0-9]/g);  //비번의 숫자 인덱스 검색
	var chk_eng = pw1.search(/[a-zA-Z]/g); //비번 영문자의 인덱스 검색
	//i hava a house.  /g ->a a /i ->a
	
	if(id.length < 4 || id.length > 12){
		alert("아이디는 4~12자까지 가능합니다.");
		form.memberId.select();     //영역 선택
		return false;
	}
	
	if(!regExPw.test(pw1) || chk_num < 0 || chk_eng < 0){ //-1이하는 찾지 못함
		alert("비밀번호는 영문자와 숫자 조합으로 6~15자까지 가능합니다.");
		form.passwd.select();
		return false;
	}
	
	if(pw1 != pw2){  
		alert("비밀번호를 동일하게 입력해 주세요.");
		form.passwd_confirm.select();
		return false;
	}
	
	if(name==""){
		alert("이름을 입력해 주세요.");
		form.name.focus();  //커서 위치
		return false;
	}
	
	form.submit();
}