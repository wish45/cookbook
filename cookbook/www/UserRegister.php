<?php

	


    error_reporting(0);//�̰��߰��ϴϱ� ���������, �ʱ�ȭ���� ���� ������ ��Ÿ���� ���� �����ε� , �� ��������ִ� �ڵ�.
    $con = mysqli_connect("localhost", "root", "123123", "db");
    
	mysqli_set_charset($con,"utf8");
	
    //$userID = isset($_POST["userssID"])?$_POST["userID"]:'';
    //$userPassword = isset($_POST["userPassword"])?_POST["userPassword"]:'';
    //$userGender = isset($_POST["userGender"])?_POST["userGender"]:'';
    //$userMajor = isset($_POST["userMajor"])?_POST["userMajor"]:'';
    //$userEmail = isset($_POST["userEmail"])?_POST["userEmail"]:'';
	
   $userID = $_POST["userID"];
   $userPassword=$_POST["userPassword"];
   $userGender=$_POST["userGender"];
   $userMajor=$_POST["userMajor"];	
   $userEmail=$_POST["userEmail"];

    $statement = mysqli_prepare($con, "INSERT INTO USERSS VALUES (?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sssss", $userID, $userPassword, $userGender, $userMajor, $userEmail);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);

	echo "����"
?>