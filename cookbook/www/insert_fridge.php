<?php
    error_reporting(E_ALL);
    ini_set('display_errors',1);

    $link=mysqli_connect("localhost","root","123123", "db" );
    if (!$link)
    {
        echo "MySQL 접속 에러 : ";
        echo mysqli_connect_error();
        exit();
    }

    mysqli_set_charset($link,"utf8");
    $sql = "INSERT INTO fridge(item_name) VALUES('$_GET[item_name]')";

    session_start();

    //$result = mysql_query( $sql, $connect);
    $result=mysqli_query($link,$sql);


    echo "$result";


    mysqli_close($link);

?>