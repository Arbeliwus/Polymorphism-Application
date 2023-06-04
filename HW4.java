import java.util.*;
import java.io.*;
import java.time.*;
class  HW4
{
	public static void main(String [] args)throws Exception
	{
		List<Flight> flightList=Flight.GetAllFlight();//存取資料
		Flight.takeOnSort(flightList);//起飛時間排列
		Flight.printAllFlight(flightList);//顯示所有航班資訊
		Set<String> Destinationlist=Flight.getDestinationList(flightList);//統計並取得所有目的地
		Flight.printAllDestination(Destinationlist);
		Flight.userSearchFlight(flightList);//使用者搜尋該航班資訊
	}	
}

class Flight implements Comparable<Flight>
{
	private String flightNum;
	private String destination;
	private LocalTime takeOn;
	private LocalTime takeOff;

	//Constructor
	Flight(String flightNum,String destination,LocalTime takeOn,LocalTime takeOff)
	{
		//即使沒有在main中使用,method內部可能會被使用
		this.flightNum=flightNum;
		this.destination=destination;
		this.takeOn=takeOn;
		this.takeOff=takeOff;
	}
	//取得航班資料
	public static List<Flight> GetAllFlight()throws Exception
	{
		List<Flight> Fligtlist=new LinkedList<>();//注意使用的是Linkedlist
		Scanner scn=new Scanner(new File("ChinaAirLine.csv"),"UTF-8");//峰哥給的檔案有編碼問題= =
		scn.nextLine();//除首行
		while(scn.hasNext())//一行一行資料切割處理
		{
			String[] data=scn.nextLine().split("[,:]");//[]為正規表示法,可包含多個symbol
			LocalTime takeOn=LocalTime.of(Integer.parseInt(data[2]),Integer.parseInt(data[3]));//localTime.of(mm,ss)
			LocalTime takeOff=LocalTime.of(Integer.parseInt(data[4]),Integer.parseInt(data[5]));
			Fligtlist.add(new Flight(data[0],data[1],takeOn,takeOff));
		}
		scn.close();
		return Fligtlist;
	}
	//顯示所有航班資訊
	public static void printAllFlight(List<Flight> flightList)
	{
		for(Flight i:flightList)
			System.out.println("flightNum:"+i.flightNum+"\tdestination:"+i.destination+"\ttakeOn:"+i.takeOn+"\ttakeOff:"+i.takeOff);
	}
	//依照takeOn排序資料
	public static void takeOnSort(List<Flight> flightList)
	{
		Collections.sort(flightList);//CompareTo專用sort(從小到大)
	}
	//重寫CompareTo
	public int compareTo(Flight flight)
	{
		return this.takeOn.compareTo(flight.takeOn);//只會回傳-1,0,1
	}
	//存取所有目的地
	public static Set<String>getDestinationList(List<Flight> flightList)
	{
		Set<String> Destinationlist = new HashSet<>();
		for(Flight i:flightList)
		{
			Destinationlist.add(i.destination);
		}
		return Destinationlist;
	}
	//取得目的地
	public String getDestination()
	{
		return this.destination;
	}
	//使用者搜尋航班
	public static void userSearchFlight(List<Flight> flightList)
	{
		Scanner scn = new Scanner(System.in,"big5");//cmd編碼
        System.out.print("請輸入城市名稱: ");
		String city=scn.nextLine();
        System.out.println("你可搭乘以下華航航班抵達" + city);
		for (Flight i : flightList) 
			if (i.getDestination().equals(city)) 
				System.out.println("Flightnum:"+i.flightNum+"\ttakeOn:"+i.takeOn+"\ttakeOff:"+i.takeOff);
		scn.close();
	}
	public static void printAllDestination(Set<String> Destinationlist)
	{
		for (String item : Destinationlist) 
            System.out.println(item);
		System.out.println("目的地總數:"+Destinationlist.size());//目的地總數
	}
}	