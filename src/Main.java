import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {

	ArrayList<ArrayList<String>> resultString;
	List<Map<String, Object>> data;
	String query;
//	public static JFileChooser fileChooser = new JFileChooser();

	public void output() {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("Output3.txt"), "UTF8"));
			for (ArrayList<String> line : resultString) {
				bw.write(line + "\n");
				bw.flush();
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	

	public void naverStart(String query, String c, JFileChooser fileChooser) {

//			System.out.print("검색할 단어를 입력하세요 : ");
//			Scanner s = new Scanner(System.in);
//			query = s.next();
			resultString = new ArrayList<ArrayList<String>>();
			int count = Integer.parseInt(c);
			int start = 1;
			query = query.replaceAll(" ", "+");
			query = query.replaceAll("&", "%26");

			for (int j = 0; j < count; j++) {
				String address = "http://openapi.naver.com/search?key=41e7b6581f7c9d85d41271a9033527d1&query="
						+ query + "&target=news&start=" + String.valueOf(start) + "&display=100&ds=2000.01.01&de=2001.01.01";
				start += 100;
				try {

				Document document = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder().parse(address);

				NodeList itemList = document.getElementsByTagName("item");
				for (int i = 0; i < itemList.getLength(); i++) {

					ArrayList<String> line = new ArrayList<String>();

					Element element = (Element) itemList.item(i);

					// 기사 제목
					NodeList titleList = element.getElementsByTagName("title");
					Element titleElmnt = (Element) titleList.item(0);
					Node title = titleElmnt.getFirstChild();
					line.add(title.getNodeValue());


					// 설명
					NodeList descriptionList = element
							.getElementsByTagName("description");
					Element descriptionElmnt = (Element) descriptionList.item(0);
					Node description = descriptionElmnt.getFirstChild();
					line.add(description.getNodeValue());


					// 날짜
					NodeList dateList = element.getElementsByTagName("pubDate");
					Element dateElmnt = (Element) dateList.item(0);
					Node date = dateElmnt.getFirstChild();
					line.add(date.getNodeValue());


					// 링크
					NodeList linkList = element
							.getElementsByTagName("originallink");
					Element linkElmnt = (Element) linkList.item(0);
					Node link = linkElmnt.getFirstChild();
					line.add(link.getNodeValue());

					System.out.println("??" + i);
					resultString.add(line);

				}
				System.out.println("testing");
				excelInput();
				excelOutput(fileChooser);

			}catch(Exception ex){
				System.out.println(ex);
			}

		}
		return;
	}


	
	public void excelOutput(JFileChooser fileChooser) {
		try {
			System.out.println(fileChooser.getSelectedFile().toString()+".xls");
			// WorkBook 생성
			WritableWorkbook wb = Workbook.createWorkbook(new File(fileChooser.getSelectedFile().toString()+".xls"));

			// WorkSheet 생성
			WritableSheet sh = wb.createSheet("네이버", 0);
			
			// 열넓이 설정 (열 위치, 넓이)
			sh.setColumnView(0, 20);
			sh.setColumnView(1, 20);
			sh.setColumnView(2, 15);
			sh.setColumnView(3, 50);


			// 셀형식
			WritableCellFormat textFormat = new WritableCellFormat();
			// 생성
			textFormat.setAlignment(Alignment.LEFT);
			// 테두리
			textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

			int row = 0;

			Label label = new jxl.write.Label(0, row, "검색어", textFormat);
			sh.addCell(label);
			
			label = new jxl.write.Label(1, row, query, textFormat);
			sh.addCell(label);
			
			row++;
			
			// 헤더
			label = new jxl.write.Label(0, row, "제목", textFormat);
			sh.addCell(label);

			label = new jxl.write.Label(1, row, "링크", textFormat);
			sh.addCell(label);

			label = new jxl.write.Label(2, row, "날짜", textFormat);
			sh.addCell(label);

			label = new jxl.write.Label(3, row, "내용", textFormat);
			sh.addCell(label);

			row++;

			for (Map<String, Object> tem : data) {

				// 이름
				label = new jxl.write.Label(0, row, (String) tem.get("title"),
						textFormat);
				sh.addCell(label);
				
				// 링크
				label = new jxl.write.Label(1, row, (String) tem.get("link"),
						textFormat);
				sh.addCell(label);

				// 날짜
				label = new jxl.write.Label(2, row, (String) tem.get("date"),
						textFormat);
				sh.addCell(label);

				
				// 주소
				label = new jxl.write.Label(3, row, (String) tem.get("description"),
						textFormat);
				sh.addCell(label);

				row++;
			}
			// WorkSheet 쓰기
			wb.write();
	 
			// WorkSheet 닫기
			wb.close();
			System.out.println("Excel Done");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	
	public void excelInput(){
		System.out.println("Excel Input Start");

		data = new ArrayList<Map<String, Object>>();
		
		for(int i = 0; i < resultString.size(); i++){
			Map<String, Object> map = new HashMap<String, Object>();
			
			ArrayList<String> lines = resultString.get(i);
			
			map.put("title", lines.get(0));
			map.put("link", lines.get(3));
			map.put("date", lines.get(2));
			map.put("description", lines.get(1));
			
			System.out.println("Excel Input add");
			data.add(map);
			
		}
		System.out.println("Excel Input Done");
	}

//	public static void main(String[] args) {
//		Main main = new Main();
//		main.daumStart();
//	}
}
