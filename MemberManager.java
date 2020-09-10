import java.io.*;
class MemberManager
{
private static final String OPERATIONS[]={"add","update","getAll","getBycourse","getByContactNumber","remove"};
private static final String DATA_FILE="member.data";
private static final String COURSES[]={"c","c++","java","python","j2ee"};
public static void main(String [] gg)
{
if(gg.length==0)
{
System.out.println("Usage: java MemberManager [operation_name]");
System.out.println("Operations: [add, update, getAll, getByCourse, getByContactNumber, remove]");
return;
}
String operation=gg[0];
if(!isOperationValid(operation))
{
System.out.println("Invalid operation");
System.out.println("Please specify an operation : [add, update, getAll, getByCourse, getByContactNumber, remove]");
return;
}
if(operation.equalsIgnoreCase("add"))
{
add(gg);
}
else if(operation.equalsIgnoreCase("getAll"))
{
getAll(gg);
}
else if(operation.equalsIgnoreCase("getByContactNumber"))
{
getByContactNumber(gg);
}
else if(operation.equalsIgnoreCase("getByCourse"))
{
getByCourse(gg);
}
else if(operation.equalsIgnoreCase("update"))
{
update(gg);
}
else if(operation.equalsIgnoreCase("remove"))
{
remove(gg);
}
}//main ends
// operational functions:-
private static void add(String [] data)
{
if(data.length!=5)
{
System.out.println("Not enough data to add");
System.out.println("Four fields required: contact_number, name, course_name, fee");
return;
}
String mobileNumber=data[1];
String name=data[2];
String course=data[3];
if(!isValidCourse(course))
{
System.out.println("Invalid course "+course);
System.out.print("Valid courses are: ");
for(int i=0;i<COURSES.length;i++)
{
System.out.print(COURSES[i]+" ");
}
System.out.printf("\n");
return;
}
int fee;
try
{
fee=Integer.parseInt(data[4]);
}catch(NumberFormatException numberFormatException)
{
System.out.println("Fee shoulf be an integer type value");
return;
}
try
{
File file=new File(DATA_FILE);
RandomAccessFile raf;
raf=new RandomAccessFile(file,"rw");
String fMobileNumber;
while(raf.getFilePointer()<raf.length())
{
fMobileNumber=raf.readLine();
if(fMobileNumber.equalsIgnoreCase(mobileNumber))
{
raf.close();
System.out.println(mobileNumber+" already exists");
return;
}
raf.readLine(); // reading three times because in file
raf.readLine(); // after mobile number there are 3 more datas
raf.readLine(); // name, course and fee
}
raf.writeBytes(mobileNumber);
raf.writeBytes("\n");
raf.writeBytes(name);
raf.writeBytes("\n");
raf.writeBytes(course);
raf.writeBytes("\n");
raf.writeBytes(String.valueOf(fee));
raf.writeBytes("\n");
}catch(IOException iOException)
{
System.out.println(iOException.getMessage());
return;
}
}

private static void getAll(String [] data)
{
try
{
File file=new File(DATA_FILE);
if(file.exists()==false)
{
System.out.println("No Member");
return;
}
RandomAccessFile raf;
raf=new RandomAccessFile(file,"rw");
if(raf.length()==0)
{
System.out.println("No Member");
raf.close();
return;
}
String mobileNumber;
String name;
String course;
int fee;
int memberCount=0;
int totalFee=0;
while(raf.getFilePointer()<raf.length())
{
mobileNumber=raf.readLine();
name=raf.readLine();
course=raf.readLine();
fee=Integer.parseInt(raf.readLine());
System.out.printf("%s, %s, %s, %d\n",mobileNumber,name,course,fee);
totalFee+=fee;
memberCount++;
}
raf.close();
System.out.println("Total registrations- "+memberCount);
System.out.println("Total fee collected- "+totalFee);
}catch(IOException iOException)
{
System.out.println(iOException.getMessage());
}
}

private static void getByContactNumber(String [] data)
{
if(data.length!=2)
{
System.out.println("Invalid number of data passed");
System.out.println("Usage: java MemberManager getByContactNumber [contact_number]");
return;
}
String mobileNumber=data[1];
try
{
File file=new File(DATA_FILE);
if(file.exists()==false)
{
System.out.println("Invalid contact number");
return;
}
RandomAccessFile raf;
raf=new RandomAccessFile(file,"rw");
if(raf.length()==0)
{
raf.close();
System.out.println("Invalid contact number");
return;
}
String fMobileNumber="";
String fName="";
String fCourse="";
int fFee=0;
boolean found=false;
while(raf.getFilePointer()<raf.length())
{
fMobileNumber=raf.readLine();
if(fMobileNumber.equalsIgnoreCase(mobileNumber))
{
fName=raf.readLine();
fCourse=raf.readLine();
fFee=Integer.parseInt(raf.readLine());
found=true;
break;
}
raf.readLine();
raf.readLine();
raf.readLine();
}
raf.close();
if(found==false)
{
System.out.println("Invalid contact number");
return;
}
System.out.println("Contact Number: "+mobileNumber);
System.out.println("Name: "+fName);
System.out.println("Course: "+fCourse);
System.out.println("Fee: "+fFee);
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}
}

private static void getByCourse(String [] data)
{
if(data.length!=2)
{
System.out.println("Invalid number of data elements passed");
System.out.println("Usage: java MemberManager getByCourse [course_name]");
return;
}
String course=data[1];
if(isValidCourse(course)==false)
{
System.out.println("Invalid course");
return;
}
try
{
File file=new File(DATA_FILE);
if(file.exists()==false)
{
System.out.println("No registration against course "+course);
return;
}
RandomAccessFile raf;
raf=new RandomAccessFile(file,"rw");
if(raf.length()==0)
{
raf.close();
System.out.println("No registration against course "+course);
return;
}
String fMobileNumber="";
String fName="";
String fCourse="";
int fFee=0;
boolean found=false;
while(raf.getFilePointer()<raf.length())
{
fMobileNumber=raf.readLine();
fName=raf.readLine();
fCourse=raf.readLine();
fFee=Integer.parseInt(raf.readLine());
if(course.equalsIgnoreCase(fCourse))
{
System.out.println("Contact Number: "+fMobileNumber);
System.out.println("Name: "+fName);
System.out.println("Course: "+fCourse);
System.out.println("fee: "+fFee);
System.out.print("\n");
found=true;
}
}
raf.close();
if(found==false)
{
System.out.println("No registration against course "+course);
return;
}
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}
}

private static void update(String [] data)
{
if(data.length!=5)
{
System.out.println("Invalid number of data elements passed");
System.out.println("Usage: java MemberManager update mobile_number name course fee");
return;
}
String mobileNumber=data[1];
String name=data[2];
String course=data[3];
if(!isValidCourse(course))
{
System.out.println("Invalid course: "+course);
return;
}
int fee;
try
{
fee=Integer.parseInt(data[4]);
}catch(NumberFormatException nfe)
{
System.out.println("Fee should be an integer type value");
return;
}
try
{
File file=new File(DATA_FILE);
if(file.exists()==false)
{
System.out.println("Invalid contact number "+mobileNumber);
return;
}
RandomAccessFile raf= new RandomAccessFile(file,"rw");
if(raf.length()==0)
{
raf.close();
System.out.println("Invalid contact number "+mobileNumber);
return;
}
boolean found=false;
String fMobileNumber="";
String fName="";
String fCourse="";
int fFee=0;
while(raf.getFilePointer()<raf.length())
{
fMobileNumber=raf.readLine();
fName=raf.readLine();
fCourse=raf.readLine();
fFee=Integer.parseInt(raf.readLine());
if(fMobileNumber.equalsIgnoreCase(mobileNumber))
{
found=true;
break;
}
}
if(found==false)
{
System.out.println("Invalid Contact Number "+mobileNumber);
raf.close();
return;
}
System.out.println("Updating data of contact number- "+mobileNumber);
System.out.println("Name of candidate is: "+fName);
File tempFile=new File("tmp.tmp");
RandomAccessFile tempRaf=new RandomAccessFile(tempFile,"rw");
tempRaf.setLength(0);
raf.seek(0); //To move pointer at the beginning of file
while(raf.getFilePointer()<raf.length())
{
fMobileNumber=raf.readLine();
fName=raf.readLine();
fCourse=raf.readLine();
fFee=Integer.parseInt(raf.readLine());
if(fMobileNumber.equalsIgnoreCase(mobileNumber)==false)
{
tempRaf.writeBytes(fMobileNumber+"\n");
tempRaf.writeBytes(fName+"\n");
tempRaf.writeBytes(fCourse+"\n");
tempRaf.writeBytes(fFee+"\n");
}
else
{
tempRaf.writeBytes(mobileNumber+"\n");
tempRaf.writeBytes(name+"\n");
tempRaf.writeBytes(course+"\n");
tempRaf.writeBytes(fee+"\n");
}
}
raf.setLength(0);
raf.seek(0);
tempRaf.seek(0);
while(tempRaf.getFilePointer()<tempRaf.length())
{
raf.writeBytes(tempRaf.readLine()+"\n");
}
raf.setLength(tempRaf.length());
tempRaf.setLength(0);
raf.close();
tempRaf.close();
System.out.println("Data updated");
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}
}

private static void remove(String [] data)
{
if(data.length!=2)
{
System.out.println("Invalid number of data elements passed");
System.out.println("Usage: java MemberManager update mobile_number");
return;
}
String mobileNumber=data[1];
try
{
File file=new File(DATA_FILE);
if(file.exists()==false)
{
System.out.println("Invalid contact number "+mobileNumber);
return;
}
RandomAccessFile raf= new RandomAccessFile(file,"rw");
if(raf.length()==0)
{
raf.close();
System.out.println("Invalid contact number "+mobileNumber);
return;
}
boolean found=false;
String fMobileNumber="";
String fName="";
String fCourse="";
int fFee=0;
while(raf.getFilePointer()<raf.length())
{
fMobileNumber=raf.readLine();
fName=raf.readLine();
fCourse=raf.readLine();
fFee=Integer.parseInt(raf.readLine());
if(fMobileNumber.equalsIgnoreCase(mobileNumber))
{
found=true;
break;
}
}
if(found==false)
{
System.out.println("Invalid Contact Number "+mobileNumber);
raf.close();
return;
}
System.out.println("Removing data of contact number- "+mobileNumber);
System.out.println("Name of candidate is: "+fName);
File tempFile=new File("tmp.tmp");
RandomAccessFile tempRaf=new RandomAccessFile(tempFile,"rw");
tempRaf.setLength(0);
raf.seek(0); //To move pointer at the beginning of file
while(raf.getFilePointer()<raf.length())
{
fMobileNumber=raf.readLine();
fName=raf.readLine();
fCourse=raf.readLine();
fFee=Integer.parseInt(raf.readLine());
if(fMobileNumber.equalsIgnoreCase(mobileNumber)==false)
{
tempRaf.writeBytes(fMobileNumber+"\n");
tempRaf.writeBytes(fName+"\n");
tempRaf.writeBytes(fCourse+"\n");
tempRaf.writeBytes(fFee+"\n");
}
}
raf.seek(0);
tempRaf.seek(0);
while(tempRaf.getFilePointer()<tempRaf.length())
{
raf.writeBytes(tempRaf.readLine()+"\n");
}
raf.setLength(tempRaf.length());
tempRaf.setLength(0);
raf.close();
tempRaf.close();
System.out.println("Data deleted");
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}
}

//helper functions:-
private static boolean isOperationValid(String operation)
{
operation=operation.trim();
for(int i=0;i<OPERATIONS.length;i++)
{
if(operation.equalsIgnoreCase(OPERATIONS[i])) return true;
}
return false;
}

private static boolean isValidCourse(String course)
{
course=course.trim();
for(int i=0;i<COURSES.length;i++)
{
if(course.equalsIgnoreCase(COURSES[i])) return true;
}
return false;
}
}//class MemberManager ends here