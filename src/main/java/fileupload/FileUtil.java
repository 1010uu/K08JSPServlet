package fileupload;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;

public class FileUtil {

	//파일 업로드를 처리 
	public static MultipartRequest uploadFile(HttpServletRequest req,
			String saveDirectory, int maxPostSize) {
		
		/*
		  MultipartRequest(request내장객체, 디렉토리의 물리적 경로, 파일제한용량, 인코딩방식)
		  객체를 생성함과 동시에 파일은 업로드가 완료되어 서버에 저장된다.
		  업로드가 성공하면 객체의 참조값을 반환한다.
		 */
		try {
			return new MultipartRequest(req, saveDirectory, maxPostSize, "UTF-8");
		}
		catch (Exception e) {
			/*
			 업로드에 실패하는 경우 용량 초과, 디렉토리 경로 오류 등이 대부분이다.
			 이 때에는 null값을 반환한다.  
			 */
			e.printStackTrace();
			return null;
		}
	}
}
