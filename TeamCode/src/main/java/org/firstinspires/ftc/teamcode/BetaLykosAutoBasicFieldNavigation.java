/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * This Opmode is a basic autonomous mode that can navigate the field using the accelerometer and
 * distance sensors
 *
 * This particular OpMode just executes a basic Teleop
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Template: Linear OpMode", group="BetaLykos")  // @Autonomous(...) is the other common choice
@Disabled
public class BetaLykosAutoBasicFieldNavigation extends LinearOpMode {

    /* Declare OpMode members. */
    BetaLykosHardware robot           = new BetaLykosHardware();   // Use betaLykos' hardware

    static final double MINREDBLUEDIFF = 25;
    static final double distanceBetweenButtons = 1;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.onRedAlliance = gamepad1.a;
        robot.useDistanceSensorForInitialPosition = true;
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        BetaLykosHardware.heading = robot.getHeading();

        if (robot.onRedAlliance) {
            runRedVersion();
        } else {
            runBlueVersion();
        }
    }

    void pushBeaconButton() throws InterruptedException {
        robot.moveRobotFeetRelitive(1,0,1,this);
    }


    void runRedVersion() throws InterruptedException {
        robot.moveRobotToPosition(robot.getPosition().x,10,1,false,this); // move away from the wall to be able to turn
        robot.moveRobotToPosition(1.5,7,1,true,this); // move to the area that the line is in
        robot.turnRobotToHeading(90,.5,this); // turn towards the beacon
        robot.moveRobotToPosition(1.5,6,1,false,this); // go to the left of the beacon
        robot.moveRobot(.5,0,0,telemetry); // start moving the robot to the right
        while (robot.getODSLightLevel() == 0) {} // wait until the ods sensor sees white
        robot.moveRobot(0,0,0,telemetry); // stop the robot
        robot.turnRobotToHeading(90,0.5,this); // turn towards the beacon
        robot.moveRobotToPosition( 1,7,0.5,false,this); // go forward and rely on the method to stop when it can not move
        double diff = robot.sensorRGB.red() - robot.sensorRGB.blue(); // calculate the diffence between the red and the blue channels
        if (diff > MINREDBLUEDIFF) { // if the red channel is greater than the blue channel by the minRedBlueDiffence variable
            // push the button
        } else { // if the red is not greater than blue
            // push the other button
        }
        robot.moveRobotToPosition(5.25, 5, 1, true, this); // move to behind the ball
        robot.moveRobotToPosition(5.25, 6.5, 0.5, true, this); // push the ball off the center and stop


    }

    void runBlueVersion() {

    }
}