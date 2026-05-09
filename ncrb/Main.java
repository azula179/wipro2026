package ncrb;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner snr=new Scanner(System.in);
		boolean login=loginservice.login(snr);
		
		if(!login) {
			System.out.println("Access denied !! You will be locked out after 3 unsuccesful attempts.");
			System.exit(0);
		}
		
		while(true) {
			System.out.println("\n===== Criminal Management System =====");

            System.out.println("1. Add Criminal");
            System.out.println("2. View Criminals");
            System.out.println("3. Search Criminal");
            System.out.println("4. Update Status");
            System.out.println("5. Delete Criminal");
            System.out.println("6. Exit");

            System.out.print("Choose option: ");
            int ch = snr.nextInt();
            snr.nextLine();
            
            switch (ch) {

            case 1 -> criminalservice.add(snr);
            case 2 -> criminalservice.view();
            case 3 -> criminalservice.search(snr);
            case 4 -> criminalservice.update(snr);
            case 5 -> criminalservice.delete(snr);
            case 6 -> {

                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice.");}
		}
	}
}
