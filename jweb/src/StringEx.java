
public class StringEx {

	public static void main(String[] args) {

		//url:�������� ������ ��ü�ּ�
		//uri:�������� ������ �����ּ�
		
		//substring() ���� �ڿ� ���� 
		String str = "unhappy";
		String str2 = str.substring(2);
		System.out.println(str2);
		String str3 = str.substring(str.lastIndexOf("h"));
		System.out.println(str3);
	
		//indexof ��ġ�� �˷��ִ� �ż���. 0,1,2
		String folder = "C:/upload";
		int idx = folder.indexOf("/");
		System.out.println(idx);
		String fName = folder.substring(idx+1);
		System.out.println(fName);
		
		//lastIndexOf() �ڿ������� ��ġ�� ã�� �ż���. ���°� �տ������� ����.
		String url = "http://www.jweb.com/member/memberForm.jsp";
		idx = url.lastIndexOf("/");
		System.out.println(idx);
		String path = url.substring(idx);
		
		//uri�� �̾Ƴ��µ� lastIndexOf�� �������
	}
	
	

}
