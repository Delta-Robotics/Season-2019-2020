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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Stone;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import org.firstinspires.ftc.teamcode.Stone;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "Autonomo Loading Zone", group = "Autonomos")
public class AutonomoLoadingZone extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_STONE = "Stone";
    private static final String LABEL_SKYSTONE = "Skystone";

    private static final String VUFORIA_KEY = "AVaXccL/////AAABmWhYLpdtdkCNnE7oGNW1b5SLGGFdd35wQQqtRvw0DgZd01BCX1KCuS1UVy939kq2MrRO9b8yp6KP1IxN42pZEJ6JYAcWmCw2zy8j8ol8REqLV3JpC1nMT8P2pqbUx1Qhss8CPsSsGnFm0Yh3dTh7kH10iXFVZxQVaq81qjuWqHZOz0NaXuW0euYkgernBTqr2AmTWyM1eYsFUsRTU+vpOr60FVLXsZXtoFe46Y1k4NO0pJjC2n0f/jRaGurnpDQzpUU7hZ7g35NPY4yE0rlnooTkuRIaxbXktengDN/L1uF1Vi/SJRRtGq1U3LRlPXVqvL1zVDXAt9a5hCFd3qBYZZhoB/rzO2OOTuX/1Vzakmvv ";

    //engine localizador de vuforia
    private VuforiaLocalizer vuforia;

    //engine de deteccion de objetos de tensorflow
    private TFObjectDetector tfod = null;

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

        if (opModeIsActive() && tfod != null) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

                        //arraylist de stones/skystones que se usara mas adelante
                        ArrayList<Stone> stones = new ArrayList();
                        boolean isSkystoneOnView = false;
                        //cantidad de skystones que se detectaran mas adelante
                        int skystones = 0;
                        if (updatedRecognitions != null && updatedRecognitions.size() > 0 ) {  //if se detectan mas de 0 recognitions
                            for (Recognition re : updatedRecognitions) { //por cada recogniton
                                int recognitionx = (int) re.getLeft(); //obtenemos el x del recognition
                                if (re.getLabel().equals(LABEL_SKYSTONE)) { // if el objeto reconocido es un skystone
                                    isSkystoneOnView = true;
                                    skystones += 1;
                                    stones.add(new Stone(true, recognitionx, re.estimateAngleToObject(AngleUnit.DEGREES), re.getHeight() / re.getImageHeight())); // se guarda un objeto 'Stone' en la arraylist con algunas propiedades del recogniton
                                }else if(re.getLabel().equals(LABEL_STONE)) {
                                    stones.add(new Stone(false, recognitionx, re.estimateAngleToObject(AngleUnit.DEGREES), re.getHeight() / re.getImageHeight())); // se guarda un objeto 'Stone' en la arraylist con algunas propiedades del recogniton
                                }
                            }

                            telemetry.addData("Info","Se detectaron " + skystones + " skystones de " + stones.size() + " stones"); //se manda info a la driver station
                            int index = 0;
                            for(Stone s : stones){
                                telemetry.addData("Stone", index + ": isSkystone="+s.isSkystone+" positionX="+ s.positionX + " distance=" + s.distance + " angle=" + s.angle); //se manda info de cada stone detectado a la driver station
                                index += 1;
                            }
                            telemetry.update();
                        }

                }

            }
        }

        //apagamos tensorflow para liberar recursos del sistema
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    //inicializar vuforia
    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //creamos una instancia de Vuforia y la guardamos en una variable
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    //Inicializar el engine de deteccion de objetos de tensorflow
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_STONE, LABEL_SKYSTONE);
    }
}
