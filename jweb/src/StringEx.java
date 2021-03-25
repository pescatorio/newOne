
public class StringEx {

	public static void main(String[] args) {

		//url:도메인을 포함한 전체주소
		//uri:도메인을 제외한 세부주소
		
		//substring() 문자 뒤에 오는 
		String str = "unhappy";
		String str2 = str.substring(2);
		System.out.println(str2);
		String str3 = str.substring(str.lastIndexOf("h"));
		System.out.println(str3);
	
		//indexof 위치를 알려주는 매서드. 0,1,2
		String folder = "C:/upload";
		int idx = folder.indexOf("/");
		System.out.println(idx);
		String fName = folder.substring(idx+1);
		System.out.println(fName);
		
		//lastIndexOf() 뒤에서부터 위치를 찾는 매서드. 세는건 앞에서부터 센다.
		String url = "http://www.jweb.com/member/memberForm.jsp";
		idx = url.lastIndexOf("/");
		System.out.println(idx);
		String path = url.substring(idx);
		
		//uri를 뽑아내는데 lastIndexOf를 사용하자
	}
	
	

}
