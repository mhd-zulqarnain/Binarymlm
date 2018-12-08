


<?php

if(isset($_GET['send_notification'])){
   send_notification ();
}

function send_notification()
{
	echo 'Hello';
define( 'API_ACCESS_KEY', 'AAAAPUedvLM:APA91bEldaUEy_ir5Vnqsv3zIkbZWvIOP4Obv_U9WIinYmpiNGnGcLIA5HTHUPUoNpI1fl-vz_3K8ulD6h1GKCrQGwBUbNXA-UJGcoyiZNbopgCSmfa3UfStkqCcZNt2bPcGIJhae5uP');
 
 
 
 //   $registrationIds = ;
#prep the bundle
     $msg = array
          (
          'type'=>"message",
		'sname' => $_GET['sname'],
        'uid'	=> $_GET['uid'],
        'sid'	=> $_GET['sid'], 
         'message'	=> $_GET['message']
          );
	$fields = array
			(
				'to'		=> $_REQUEST['token'],
			    'data' => $msg
			);
	
	
	$headers = array
			(
				'Authorization: key=' . API_ACCESS_KEY,
				'Content-Type: application/json'
			);
#Send Reponse To FireBase Server	
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		echo $result;
		curl_close( $ch );
}
?>
