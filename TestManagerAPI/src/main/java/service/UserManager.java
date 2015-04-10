//package service;
//
//import java.util.Calendar;
//import java.util.Date;
//
//import database.Database;
//
//public class UserManager {
//
//	private static Calendar calendar = Calendar.getInstance();
//
//	public static void addNew(User user){
//
//		Database db = new Database();
//		db.connect();
//		db.insertUser(user);
//		db.closeConnection();
//	}
//
//	public static User getData(String username){
//
//		Database db = new Database();
//		db.connect();
//		User user =  db.loadUser(username);
//		db.closeConnection();
//		return user;
//	}
//
//	public boolean checkUsername(String username){
//
//		Database db = new Database();
//		db.connect();
//		boolean result = db.usernameExists(username);
//		db.closeConnection();
//		return result;
//	}
//	
//	public boolean checkEmail(String email){
//		
//		Database db = new Database();
//		db.connect();
//		boolean result = db.emailExists(email);
//		db.closeConnection();
//		return result;
//	}
//	
//	public boolean successfulLogin(String username, String password){
//		
//		if(!checkUsername(username)){
//			return false;
//		}
//		Database db = new Database();
//		db.connect();
//		if(db.PasswordMatch(username, password)){
//			db.closeConnection();
//			return true;
//		} else {
//			db.closeConnection();
//			return false;
//		}
//	}
//
//	public boolean isBG(String username){
//
//		Database db = new Database();
//		db.connect();
//		boolean result = db.isUserBG(username);
//		db.closeConnection();
//		return result;
//	}
//
//	public boolean isInExclude(String username){
//
//		Database db = new Database();
//		db.connect();
//		boolean result = db.isUserInExclude(username);
//		db.closeConnection();
//		return result;
//	}
//
//	public void updateDietStatusOnLogin(String username){
//
//		Database db = new Database();
//		db.connect();
//		int userId = db.getUserId(username);
//		if (!db.selectPdf(userId, "Y").isEmpty()){
//			Date now = calendar.getTime();
//			Date enddate = db.getDietEnddate(username);
//			if (now.after(enddate)){
//				db.updateDietStatus(userId);
//				db.updatePdfStatus(userId);
//			}
//		}
//		db.closeConnection();
//	}
//	
//	public void updateWeight(String username, double weight){
//		
//		Database db = new Database();
//		db.connect();
//		db.insertIntoHistory(username, weight);
//		db.updateUserWeight(username, weight);
//		db.closeConnection();
//	}
//
//	public boolean[] needDiet(User user){
//
//		boolean[] needDiet = {false, false};
//		int[] weight = BMICalculator.getIdealWeight(user);
//		int targetWeight = BMICalculator.getTargetWeight(weight);
//
//		if (targetWeight > (int)user.getWeight()){
//			needDiet[0] = true;
//			needDiet[1] = true; //user needs to gain weight
//		} else if (targetWeight < (int)user.getWeight()){
//			needDiet[0] = true;
//			needDiet[1] = false; //user needs to loose weight
//		}
//		return needDiet;
//	}
//
//}
//
