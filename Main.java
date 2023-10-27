package Code;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.*;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		Teams lg = new Teams();
		Scanner scan = new Scanner(System.in);
		int c = 1;
		System.out.println("---------Football Manager---------");
		

		while (c!=7) {
			System.out.println("\n-------Main Menu-------");
			System.out.println("1-Create league"
					+ "\n2-Enter teams and details"
					+"\n3-Auto fixture"
					+"\n4-Match result"
					+"\n5-View Pointtable"
					+"\n6-League winner"
					+"\n7-Exit\n");
			c=scan.nextInt();
			switch (c){
				case 1:
					System.out.println("Create League?(1/0): ");
					int c1=scan.nextInt();
					if (c1==1 )
						lg.create_lg();
					else
						lg.use_lg();
					break;
				case 2:
					lg.create_tm();
					break;
				case 3:
					lg.autoMatch();
					break;
				case 4:
					lg.Match();
					break;
				case 5:
					lg.disp_pnt();
					break;
				case 6:
					lg.winner();
					break;
				case 7:
					System.out.println("\n"
							+ "----------------------------------------------------"
							+ "\n\tThanks For Using Football Manager."
							+ "\n----------------------------------------------------");
					break;
				default:
					System.out.println("Invalid input.");
			}
			
		}
		scan.close();
	}
}
class connect {
	public Object insert_lg;

	
	public void insert_lg(String a,int b) {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FootballManager","root", "8520");
			Statement statement;
			statement = conn.createStatement();
			String sql="insert into league(league, ttl_teams) values (?,?) ";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, a);
			ps.setInt(2,b); 
			
			int x = ps.executeUpdate();
			
			if (x>0) {
				System.out.print("League created.");
			}
			ps.close();
			statement.close();
			conn.close();
		}
		
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
	
	public void insert_tm(int a,String b) {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FootballManager","root", "8520");
			Statement statement;
			statement = conn.createStatement();
			String sql1="insert into team values (?,?) ";
			String sql2="insert into point values(?,0,0,0,0);";
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps1.setInt(1,a );
			ps1.setString(2,b); 
			ps2.setString(1,b);
			int x = ps1.executeUpdate();
			int y = ps2.executeUpdate();
			if (x>0 && y>0) {
				System.out.print("Team entered.");
				System.out.print("\nTeam Created in pointtable\n");
			}
			else {
				System.out.println("Team not inserted.");
			}
			
			ps1.close();
			ps2.close();
			statement.close();
			conn.close();
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
	public void disp_lg() {
		
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FootballManager","root", "8520");
			Statement statement;
			statement = conn.createStatement();
			String sql="Select * from league";
			ResultSet rs;
			PreparedStatement ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			int n;
			String lg;
			int i=0;
			System.out.println("Sl.no \t League name \t\t No.of teams");
			while (rs.next()) {
				i++;
				n = rs.getInt("ttl_teams");
				lg = rs.getString("league").trim();
				System.out.println("\n"+i+"\t"+lg+"\t\t\t\t"+n+"\n");
			}
			rs.close();
			ps.close();
			statement.close();
			conn.close();
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
	public void disp_tm(){
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FootballManager","root", "8520");
			Statement statement;
			statement = conn.createStatement();
			String sql="Select * from team";
			ResultSet rs;
			PreparedStatement ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			int n;
			String tm;

			int i=0;
			System.out.println("Sl.no \t Team name \t\t No.of players ");
			
			while (rs.next()) {
				i++;
				n = rs.getInt("n");
				tm = rs.getString("team").trim();

				System.out.println(i+"\t"+tm+"\t\t"+n+"\t");	
			}
			rs.close();
			ps.close();
			statement.close();
			conn.close();
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
	public void insert_pnt(String a, int b) {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FootballManager","root", "8520");
			Statement statement;
			statement = conn.createStatement();
			if (b==1) {
				String sql1="update point set draw=draw+1 where tm_name=?;";
				String sql2="update point set point=point+1 where tm_name=?;";
				PreparedStatement ps1 = conn.prepareStatement(sql1);
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps1.setString(1,a);
				ps2.setString(1,a);
				ps1.executeUpdate();
				ps2.executeUpdate();
				ps1.close();
				ps2.close();
			}
			else if(b==0) {
				String sql1="update point set lose=lose+1 where tm_name=?";
				PreparedStatement ps1 = conn.prepareStatement(sql1);
				ps1.setString(1,a);
				ps1.executeUpdate();
				ps1.close();
			}
			else if(b==3) {
				String sql1="update point set win=win+1 where tm_name=?; ";
				String sql2="update point set point=point+3 where tm_name=?;";
				PreparedStatement ps1 = conn.prepareStatement(sql1);
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps1.setString(1,a);
				ps2.setString(1,a);
				ps1.executeUpdate();
				ps2.executeUpdate();
				ps1.close();
				ps2.close();
			}
			else {
				System.out.println("\nInvalid point");
			}
			
			statement.close();
			conn.close();
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
public void disp_pnt() {
		
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FootballManager","root", "8520");
			Statement statement;
			statement = conn.createStatement();
			String sql="Select * from point ORDER BY point DESC";
			ResultSet rs;
			PreparedStatement ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			int w;
			int l;
			int d;
			int pnt;
			String tm;
			int i=0;
			System.out.println("Sl.no \t Team name \t\t win \tlose \tdraw \tpoint");
			while (rs.next()) {
				i++;
				w = rs.getInt("win");
				l = rs.getInt("lose");
				d = rs.getInt("draw");
				pnt = rs.getInt("point");
				tm = rs.getString("tm_name");
				System.out.println("\n"+i+"\t"+tm+"\t\t"+w+"\t"+l+"\t"+d+"\t"+pnt+"\n");
			}
			rs.close();
			ps.close();
			statement.close();
			conn.close();
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
	public void winner() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FootballManager","root", "8520");
			Statement statement;
			statement = conn.createStatement();
			String sql="Select * From point ORDER BY point DESC LIMIT 1;";
			ResultSet rs;
			PreparedStatement ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			int a;
			String tm;
			while (rs.next()) {			
				a = rs.getInt("point");
				tm = rs.getString("tm_name").trim();
				System.out.println("The Winner of the Tournament "+tm+" by "+a);	
			}
			rs.close();
			ps.close();
			statement.close();
			conn.close();
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
}

class Teams extends connect{
	int n;
	String lg;
	connect cn = new connect();
	Scanner scan_t = new Scanner(System.in);
	
	//team variable
	class Team {
	    String name;

	    Team(String name) {
	        this.name = name;
	    }
	}
	//autofixt variable
	class m{
		Team tm1;
		Team tm2;
		
		m(Team teams,Team teams2){
			this.tm1=teams;
			this.tm2=teams2;
		}
	}
	Team[] teams = new Team[100];
	m[] matches = new m[1000];
	//autofixture variable
	int count=0;
	
	//match variable
	int pos;
	int point_l,point_r;
	
	
	public void create_lg() {
		System.out.println("Enter League name: ");
		lg=scan_t.nextLine();
		System.out.println("Enter no.of teams: ");
		n=scan_t.nextInt();
		cn.insert_lg(lg, n);
	}
	public void use_lg() {
		System.out.println("Choose from leagues");
		cn.disp_lg();
		System.out.println("\nChoose a league: ");
		lg=scan_t.nextLine();
		System.out.println("\nEnter no.of teams from the list:");
		n=scan_t.nextInt();
	}
	public void create_tm() {
		String tm_name;
		int no;
		
		System.out.println("Enter all teams and no.of players.");
		for(int i =0; i<n ; i++) {
			System.out.println("\nEnter team name: ");
			tm_name=scan_t.next();
			teams[i] = new Team(tm_name);
			System.out.println("\nEnter no.of Players: ");
			no=scan_t.nextInt();
			cn.insert_tm(no, tm_name);
		}
		for (int i=0;i<n;i++) {
		System.out.println(teams[i].name);
		}
	}
	
	public void autoMatch() {
		
		for(int i =0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if (i!=j) {
					matches[count]=new m(teams[i],teams[j]);	
					count++;
				}
			}
		}
		for(int i=0;i<count;i++) {
			System.out.println("\n"+matches[i].tm1.name);
			System.out.println("\tvs\t"+matches[i].tm2.name);
		}
	}
	
	public void Match() {
		pos=1;
		int c=0;
		int y=0;
		System.out.println(matches[pos].tm1.name);
		System.out.println("\tvs\t"+matches[pos].tm2.name+"\n");
		System.out.println("Was the game draw(1/0):");
		c=scan_t.nextInt();
		if (c==1) {
			cn.insert_pnt(matches[pos].tm1.name,1);
			cn.insert_pnt(matches[pos].tm2.name,1);
		}
		else if(c==0) {
			System.out.println("Did "+matches[pos].tm1.name+" won the game(1/0): ");
			y=scan_t.nextInt();
			if (y==0) {
				cn.insert_pnt(matches[pos].tm2.name, 3);
				cn.insert_pnt(matches[pos].tm1.name, 0);
			}
			else if(y==1) {
				cn.insert_pnt(matches[pos].tm2.name, 0);
				cn.insert_pnt(matches[pos].tm1.name, 3);
			}
			else{
				System.out.println("Inavlid input");
			}
		}
		else{
			System.out.println("Inavlid input");
		}
		pos=pos+1;
	}
}


	

