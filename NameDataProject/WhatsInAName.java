import java.io.*;
import java.util.*;

public class WhatsInAName {
	public void clearScreen() {  
    	System.out.print("\033[H\033[2J");  
    	System.out.flush();  
	}
	public void barChart(){
		Scanner sc = new Scanner(System.in);
		clearScreen();
		int dataStage = 0;
		String name = "";
		String state = "";
		int startYear = 0;
		int endYear = 0;
		while(1 < 2){
			if(dataStage == 0){
				System.out.println("Enter name:");
				name = sc.nextLine();
				dataStage++;
			}
			if(dataStage == 1){
				System.out.println("Enter start year:");
				startYear = sc.nextInt();
				if(startYear >= 1910 && startYear <= 2018){
					System.out.println("Enter end year:");
					endYear = sc.nextInt();
					if(endYear <= 2018 && startYear < endYear){
						dataStage++;	
					}else{
						System.out.println("Invalid input for year! Try again.");
					}
				}else{
					System.out.println("Invalid input for year! Try again.");
				}
			}
			if(dataStage == 2){
				System.out.println("Enter postal code for state:");
				state = sc.nextLine();
				if(readData(state) != null){
					dataStage++;
					break;
				}else{
					System.out.println("Invalid input for postal code! Try again.");
				}
			}
		}
		String[][] data = readData(state);
		int mostPopularAmt = 0;
		int mostPopularYear = 0;
		int leastPopularAmt = 0;
		int leastPopularYear = 0;
		for(int i = 0; i < data.length; i++){
			if(data[i][3].equals(name)){
				if(Integer.parseInt(data[i][4]) > mostPopularAmt){
					mostPopularAmt = Integer.parseInt(data[i][4]);
					mostPopularYear = Integer.parseInt(data[i][2]);
				}
				if(Integer.parseInt(data[i][4]) < leastPopularAmt){
					leastPopularAmt = Integer.parseInt(data[i][4]);
					leastPopularYear = Integer.parseInt(data[i][2]);
				}
			}
		}
		System.out.println("Historical Trends for " + name + " in " + state + ", " + startYear + "-" + endYear + ":");
		int chartHeight = mostPopularAmt / 5;
		int chartWidth = endYear - startYear;
		int[] barData = new int[chartWidth + 1];
		for(int i = 0; i < data.length; i++){
			if(data[i][3].equals(name)){
				if(Integer.parseInt(data[i][2]) >= startYear && Integer.parseInt(data[i][2]) <= endYear){
					barData[endYear - Integer.parseInt(data[i][2])] = Integer.parseInt(data[i][4]);
				}
			}
		}
		for(int i = 0; i < barData.length; i++){
			System.out.print(startYear + i +":");
			for(int j = 0; j < barData[i] / 10; j++){
				System.out.print(".");
			}
			System.out.println();
		}
		System.out.println("Press enter to be taken back to main menu!");
		String wait = sc.nextLine();
		clearScreen();
		init();

	}
	public void topNames(){
		clearScreen();
		Scanner sc = new Scanner(System.in);
		int dataStage = 0;
		String gender = "";
		String state = "";
		int year = 0;
		while(1 < 2){
			if(dataStage == 0){
				System.out.println("Enter year:");
				year = sc.nextInt();
				if(year >= 1910 && year <= 2018){
						dataStage++;	
				}else{
					System.out.println("Invalid input for year! Try again.");
				}
			}
			if(dataStage == 1){
				System.out.println("Enter gender:");
				gender = sc.nextLine();
				if(gender.equals("M") || gender.equals("F")){
						dataStage++;	
				}else{
					System.out.println("Invalid input for gender! Try again.");
				}
			}
			if(dataStage == 2){
				System.out.println();
				System.out.println("Enter postal code for state:");
				state = sc.nextLine();
				if(readData(state) != null){
					dataStage++;
					break;
				}else{
					System.out.println("Invalid input for postal code! Try again.");
				}
			}
		}
		String[][] data = readData(state);
		String[] names = new String[10];
		int[] amt = new int[10];
		int stage = 0;
		for(int i = 0; i < 10; i++){
			if(i>=1){
				for(int j = 0; j < data.length; j++){
					if(Integer.parseInt(data[j][2]) == year && data[j][1].equals(gender)){
						if(Integer.parseInt(data[j][4]) > amt[i] && Integer.parseInt(data[j][4]) < amt[i-1]){
							amt[0] = Integer.parseInt(data[j][4]);
							names[0] = data[j][3];
						}
					}
				}
			}else{
				for(int j = 0; j < data.length; j++){
					if(Integer.parseInt(data[j][2]) == year && data[j][1].equals(gender)){
						if(Integer.parseInt(data[j][4]) > amt[0]){
							amt[0] = Integer.parseInt(data[j][4]);
							names[0] = data[j][3];
						}
					}
				}
			}
		}
		String genderFormal = "";
		if(gender.equals("M")){
			genderFormal = "Male";
		}else{
			genderFormal = "Female";
		}
		System.out.println("Top 10 " + genderFormal + " Names in " + state + " for " + year + ":");
		for(int i = 0;i<10;i++){
			System.out.println((i+1)+ ". " + names[i] + " " + amt[i]);
		}
		System.out.println("Press enter to be taken back to main menu!");
		String wait = sc.nextLine();
		clearScreen();
		init();
	}
	public void init(){
		Scanner sc = new Scanner(System.in);
		System.out.println("What data do you want? (1. Historical Bar Chart, 2. Top 10 Names, 3. Exit)");
		System.out.println("enter 1, 2, or 3:");
		int mode = sc.nextInt();
		if(mode == 1 || mode == 2 || mode == 3){
			if(mode == 1){
				barChart();
			}
			if(mode == 2){
				topNames();
			}
			if(mode == 3){
				System.exit(0);
			}
		}else{
			System.out.println("Incorrect input! Please use 1, 2, or 3!");
			init();
		}

	}
	public static void main(String[] args) {
		WhatsInAName mainClass = new WhatsInAName();
		mainClass.init();
	}
	public static String[][] readData(String stateCode) {
		ArrayList<String[]> data = new ArrayList<>();
		try {
			Scanner f = new Scanner(new FileReader(stateCode + ".TXT"));
			while (f.hasNext()) {
				data.add(f.nextLine().split(","));
			}
			f.close();
			return data.toArray(new String[][]{{}});
		}
		catch (FileNotFoundException e) {
			return null;
		}
	}
}
