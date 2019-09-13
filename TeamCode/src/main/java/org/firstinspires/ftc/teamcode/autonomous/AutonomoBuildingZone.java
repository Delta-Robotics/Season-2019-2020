/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.SkyStonesPattern;
import org.firstinspires.ftc.teamcode.Stone;

import java.util.ArrayList;
import java.util.List;

/**
 * This 2019-2020 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Skystone game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@TeleOp(name = "Autonomo Building Zone", group = "Autonomos")
public class AutonomoBuildingZone extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    private static final String VUFORIA_KEY = "AVaXccL/////AAABmWhYLpdtdkCNnE7oGNW1b5SLGGFdd35wQQqtRvw0DgZd01BCX1KCuS1UVy939kq2MrRO9b8yp6KP1IxN42pZEJ6JYAcWmCw2zy8j8ol8REqLV3JpC1nMT8P2pqbUx1Qhss8CPsSsGnFm0Yh3dTh7kH10iXFVZxQVaq81qjuWqHZOz0NaXuW0euYkgernBTqr2AmTWyM1eYsFUsRTU+vpOr60FVLXsZXtoFe46Y1k4NO0pJjC2n0f/jRaGurnpDQzpUU7hZ7g35NPY4yE0rlnooTkuRIaxbXktengDN/L1uF1Vi/SJRRtGq1U3LRlPXVqvL1zVDXAt9a5hCFd3qBYZZhoB/rzO2OOTuX/1Vzakmvv ";

    //engine localizador de vuforia
    private VuforiaLocalizer vuforia;

    //engine de deteccion de objetos de tensorflow
    private TFObjectDetector tfod;

    //patron de los skystones
    SkyStonesPattern skystonespattern;

    @Override
    public void runOpMode() {
        // tensorflow usa la camara de vuforia, asi que hacemos esto primero
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            //si el dispositivo no es compatible con tensorflow lite, se manda un mensaje a la driver station.
            telemetry.addData("Sebas", "Ow! Este dispositivo no es compatible con TensorFlow Lite!");
        }

        //activamos la camara de deteccion de objetos antes de que empieze el opmode
        if (tfod != null) {
            tfod.activate();
        }

        //esperar a que se presione play en la driver station
        telemetry.addData("Sebas", "Dale a play para que empieze lo bueno.");
        telemetry.update();
        waitForStart();

        boolean alreadyFoundSkystonesPattern = false;
        List<Recognition> lastUpdatedRecognitions = null;

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if(updatedRecognitions != null){
                        lastUpdatedRecognitions = updatedRecognitions;
                    }else{
                        updatedRecognitions = lastUpdatedRecognitions;
                    }

                        ArrayList stones = new ArrayList();
                        boolean isSkystoneOnView = false;
                        int skystones = 0
                        if (updatedRecognitions.size() > 0) {
                            for (Recognition re : updatedRecognitions) {
                                int recognitionx = (int) re.getLeft(); //obtenemos el x del recognition
                                if (re.getLabel() == LABEL_SECOND_ELEMENT) {
                                    isSkystoneOnView = true;
                                    skystones += 1;
                                    stones.add(new Stone(true, recognitionx));
                                }else if(re.getLabel() == LABEL_FIRST_ELEMENT) {
                                    stones.add(new Stone(false, recognitionx));
                                }
                            }

                            telemetry.addData("Se detectaron" + skystones + " skystones de " + stones.size() + " stones");
                            int index = 0;
                            for(Stone s : stones){
                                telemetry.addData("Stone " + index + ": isSkystone="+s.isSkystone+" positionX="s.positionX);
                                index += 1;
                            }
                            telemetry.update();
                        }

                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    //inicializamos vuforia
    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //creamos una instancia de Vuforia y la guardamos en una variable
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        //no se necesitan cargar los trackeables
    }

    //Iniciamos el engine de deteccion de objetos de tensorflow
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
