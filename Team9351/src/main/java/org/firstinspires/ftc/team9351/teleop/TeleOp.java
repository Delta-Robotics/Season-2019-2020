package org.firstinspires.ftc.team9351.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.team9351.hardware.Hardware;
import org.firstinspires.ftc.team9351.hardware.MecanumWheels;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="TeleOps") //se define que la clase se trata de un teleop con una annotation
public class TeleOp extends LinearOpMode { //la clase extendera a otra llamada 'LinearOpMode'

    //objeto que contiene el hardware del robot
    Hardware hdw;

    long runmillis;
    long disappearmillis;

    MecanumWheels mecanumWheels;

    @Override
    public void runOpMode(){
        hdw = new Hardware(hardwareMap); //init hardware

        mecanumWheels = new MecanumWheels();

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

            telemetry.addData("wheelUpRightPower", mecanumWheels.wheelUpRightPower);
            telemetry.addData("wheelUpLeftPower", mecanumWheels.wheelUpLeftPower);
            telemetry.addData("wheelDownRightPower", mecanumWheels.wheelDownRightPower);
            telemetry.addData("wheelDownLeftPower", mecanumWheels.wheelDownLeftPower);

            //set power de los motores
            hdw.wheelUpRight.setPower(mecanumWheels.wheelUpRightPower);
            hdw.wheelUpLeft.setPower(mecanumWheels.wheelUpLeftPower);
            hdw.wheelDownRight.setPower(mecanumWheels.wheelDownRightPower);
            hdw.wheelDownLeft.setPower(mecanumWheels.wheelDownLeftPower);

            telemetry.update();  //manda los mensajes telemetry a la driver station
        }

    }

    public void startA() {
        //si cualquiera de los 2 triggers es presionado (mayor que 0), el robot avanzara a la mitad
        //de velocidad. el fin de esto es para que el arrastrar la foundation en el endgame no sea
        //tan arriesgado y haya menos probabilidad de que tiremos cualquier stone
        if (gamepad1.left_trigger > 0 || gamepad1.right_trigger > 0) {
            mecanumWheels.joystick(gamepad1, 0.5);
        } else {
            mecanumWheels.joystick(gamepad1, 1);
        }
    }

    public void startB(){
    }

}
