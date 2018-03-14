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


//POST 값을 읽어온다.
$name=isset($_POST['country']) ? $_POST['country'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


if ($name !="" ){

    $sql="select * from Recipe where name='$name'";

    $result=mysqli_query($link,$sql);
    $data = array();
    if($result){

        $row_count = mysqli_num_rows($result);

        if ( 0 == $row_count ){

            array_push($data,
                array( 'id'=>'-1',
                'name'=>'N',
                'address'=>$name)
            );

            if (!$android) {

                echo "'";
                echo $name;
                echo "'은 찾을 수 없습니다.";

                echo "<pre>";
                print_r($data);
                echo '</pre>';
            }else
            {
                header('Content-Type: application/json; charset=utf8');
                $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
                echo $json;
            }

        }
        else{

            while($row=mysqli_fetch_array($result)){
                array_push($data,
                    array('id'=>$row["id"],
                    'name'=>$row["name"],
                    'ingred'=>$row["ingred"],
                    'recipe'=>$row["recipe"],
                    'url'=>$row["imageURL"]
                ));
            }



            if (!$android) {
                echo "<pre>";
                print_r($data);
                echo '</pre>';
            }else
            {
                header('Content-Type: application/json; charset=utf8');
                $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
                echo $json;
            }
        }


        mysqli_free_result($result);

    }
    else{
        echo "SQL문 처리중 에러 발생 : ";
        echo mysqli_error($link);
    }
}
else {
    echo "검색할 요리를 입력하세요 ";
}


mysqli_close($link);

?>



<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
   <body>

      <form action="<?php $_PHP_SELF ?>" method="POST">
         요리 이름: <input type = "text" name = "country" />
         <input type = "submit" />
      </form>

   </body>
</html>
<?php
}


?>
