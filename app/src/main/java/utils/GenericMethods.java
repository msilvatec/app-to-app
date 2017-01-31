package utils;

public class GenericMethods {
	
	public static int getTypeOfInstallment(String type){
		int typeParcel = 0; // default is "� VISTA"
		
		switch (type) {
		case "À VISTA":
			typeParcel = 0;
			break;
		case "LOJISTA":
			typeParcel = 1;
			break;
		}
		
		return typeParcel;
	}
	
}
