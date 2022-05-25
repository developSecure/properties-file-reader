%dw 2.0
import * from dw::core::Strings

fun trimString(s: String) = 
 	if(s startsWith("get")) 
 		replace(substringBeforeLast(s,":"),/(\\)/) with("/")
 	else if (s contains("application"))
 		 substringBeforeLast(replace(substringBeforeLast(s,":"),/(\\)/) with("/"),":")
 	else 
 		replace(substringBeforeLast(s,":"),/(\\)/) with("/")