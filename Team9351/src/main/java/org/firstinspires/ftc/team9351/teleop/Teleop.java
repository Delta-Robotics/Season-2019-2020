package org.firstinspires.ftc.team9351.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.team9351.hardware.Hardware;

@TeleOp(name="TeleOp", group="TeleOps") //se define que la clase se trata de un teleop con una annotation
public class Teleop extends LinearOpMode { //la clase extendera a otra llamada 'LinearOpMode'

    //objeto que contiene el hardware del robot
    Hardware hdw;

    long runmillis;
    long disappearmillis;

    //power de las llantas
    public double wheelUpRightPower = 0;
    public double wheelUpLeftPower = 0;
    public double wheelDownRightPower = 0;
    public double wheelDownLeftPower = 0;

    //true = mecanum
    //false = omniwheels
    public boolean wheelMode = true;

    @Override
    public void runOpMode(){
        hdw = new Hardware(hardwareMap); //init hardware

        telemetry.addData("Sebas", "All set?"); //manda un mensaje a la driver station
        telemetry.update();

        waitForStart(); //espera hasta que se presione <play> en la driver station

        runmillis = System.currentTimeMillis();
        disappearmillis = runmillis + (3 * 1000); //el tiempo en el que desaparecera el mensaje "GO!!!"

        while(opModeIsActive()){

            if(System.currentTimeMillis() < disappearmillis) { //se ejecuta cuando no hayan pasado mas de 3 segundos (3000 ms) desde que se dio a <play>
                telemetry.addData("Sebas", "GO!!!");
            }

            startA(); //movimientos del start A
            startB();//movimientos del start B

            telemetry.addData("Info", "wheelUpRightPower = " + wheelUpRightPower);
            telemetry.addData("Info", "wheelUpLeftPower = " + wheelUpLeftPower);
            telemetry.addData("Info", "wheelDownRightPower = " + wheelDownRightPower);
            telemetry.addData("Info", "wheelDownLeftPower =" + wheelDownLeftPower);

            //set power de los motores
            hdw.wheelUpRight.setPower(wheelUpRightPower);
            hdw.wheelUpLeft.setPower(wheelUpLeftPower);
            hdw.wheelDownRight.setPower(wheelDownRightPower);
            hdw.wheelDownLeft.setPower(wheelDownLeftPower);

            telemetry.update();  //manda los mensajes telemetry a la driver station
        }

    }

    public void startA(){

        //cambiar de omni a mecanum y viceversa con los bumpers left y right
        //el proposito de esto es poder alternar entre llantas omni y mecanum facilmente sin tener que reprogramar nada
        //el robot empieza por default con modo mecanum
        //left: mecanum
        //right: omni
        if(gamepad1.left_bumper){
            wheelMode = true;
        }else if(gamepad1.right_bumper) {
            wheelMode = false;
        }

        //si cualquiera de los 2 triggers es presionado (mayor que 0), el robot avanzara a la mitad de velocidad
        //el fin de esto es para que el arrastrar la foundation en el endgame no sea tan arriesgado y
        //haya menos probabilidad de que tiremos cualquier skyscraper
        if(gamepad1.left_trigger > 0 || gamepad1.right_trigger > 0){
            if(wheelMode) {
                mecanum(0.5);
                telemetry.addData("Info", "wheelMode: Mecanum");
            }else{
                omni(0.5);
                telemetry.addData("Info", "wheelMode: Omni");
            }
        }else{
            //si no, el robot avanzara a la velocidad normal
            if(wheelMode) {
                mecanum(1);
                telemetry.addData("Info", "wheelMode: Mecanum");
            }else{
                omni(1);
                telemetry.addData("Info", "wheelMode: Omni");
            }
        }
    }

    public void mecanum(double speed){
        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;
        wheelUpRightPower = (r * Math.cos(robotAngle) + rightX) / speed;
        wheelUpLeftPower = (r * Math.sin(robotAngle) - rightX) / speed;
        wheelDownRightPower = (r * Math.sin(robotAngle) + rightX) / speed;
        wheelDownLeftPower = (r * Math.cos(robotAngle) - rightX) / speed;
    }

    public void omni(double speed){
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;
        double leftPower = 0;
        double rightPower = 0;

        leftPower    = Range.clip(drive + turn, -1.0, 1.0) / speed;
        rightPower   = Range.clip(drive - turn, -1.0, 1.0) / speed;

        wheelUpRightPower = rightPower;
        wheelUpLeftPower = leftPower;
        wheelDownRightPower = rightPower;
        wheelDownLeftPower = leftPower;
    }

    public void startB(){

    }

}
