/* Copyright (c) 2017 FIRST. All rights reserved.
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

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Driving", group="Iterative Opmode")
//@Disabled
public class BasicOpMode_Iterative extends OpMode
{
    // Declare OpMode members.
    NewHardware robot = new NewHardware();
    private ElapsedTime runtime = new ElapsedTime();
    private double verticalDeadZone = 0.1;
    private double horizontalDeadZone = 0.1;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");


        robot.Initialize(hardwareMap);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.liftBot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.liftBot2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);




        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower = 0;
        double rightPower = 0;
        double armPower = 0;

        Move(2);
        Sucker();
        liftBot();
        flipper(0.3);
        mineralCrater();

       /* if(gamepad1.dpad_up)
        {
            robot.rightDrive.setPower(-1);
            robot.leftDrive.setPower(-1);
        }else
            {
                robot.rightDrive.setPower(0);
                robot.leftDrive.setPower(0);
            }

        if(gamepad1.dpad_down)
        {
            robot.leftDrive.setPower(1);
            robot.rightDrive.setPower(1);

        }else
            {
                robot.rightDrive.setPower(0);
                robot.leftDrive.setPower(0);
            }
        if(gamepad1.dpad_right)
        {
            robot.leftDrive.setPower(1);
            robot.rightDrive.setPower(-1);
        }else{
            robot.rightDrive.setPower(0);
            robot.leftDrive.setPower(0);
        }
        if(gamepad1.dpad_left)
        {
            robot.leftDrive.setPower(-1);
            robot.rightDrive.setPower(1);
        }else{
            robot.rightDrive.setPower(0);
            robot.leftDrive.setPower(0);
        }

        if (Math.abs(gamepad1.left_stick_y) > verticalDeadZone && Math.abs(gamepad1.left_stick_x) <= horizontalDeadZone) {
            //leftPower = gamepad1.left_stick_y;
            y = gamepad1.left_stick_y;
        }

        if (Math.abs(gamepad1.left_stick_x) > horizontalDeadZone && Math.abs(gamepad1.left_stick_y) <= verticalDeadZone) {
            x = gamepad1.left_stick_x;
            //rightPower = -gamepad1.left_stick_x;
        }

        if (Math.abs(gamepad1.left_stick_x) <= horizontalDeadZone && Math.abs(gamepad1.left_stick_y) <= verticalDeadZone) {
            y = 0;
            x = 0;
        }

        if (gamepad2.right_bumper) {
            robot.liftBot2.setPower(-0.5);
            robot.liftBot.setPower(0.5);
        } else {
            robot.liftBot2.setPower(0);
            robot.liftBot.setPower(0);

        }
        if (gamepad2.left_bumper) {
            robot.liftBot2.setPower(0.5);
            robot.liftBot.setPower(-0.5);
        } else {
            robot.liftBot2.setPower(0);
            robot.liftBot.setPower(0);

        }

        robot.leftDrive.setPower(y+x);
        robot.rightDrive.setPower(y-x);

        if (gamepad1.right_bumper) {
            robot.mineralCollect.setPower(1);
        } else {
            robot.mineralCollect.setPower(0);
        }
        if (gamepad1.left_bumper) {
            robot.mineralCollect.setPower(-0.8);
        } else {
            robot.mineralCollect.setPower(0);
        }
        if (Math.abs(gamepad2.left_stick_y) > verticalDeadZone) {
            armPower = gamepad2.left_stick_y;

        } else {
            robot.mineralArm.setPower(0);
        }
        robot.mineralArm.setPower(armPower);


        if (gamepad2.a) {
            robot.liftBot.setPower(-1.3);
            robot.liftBot2.setPower(1.3);

        }else{
            robot.liftBot2.setPower(0);
            robot.liftBot.setPower(0);
        }
        if(gamepad2.x){
            robot.mineralArm.setPower(0.5);

        }else
            {
                robot.mineralArm.setPower(0);
            }
            if(gamepad2.b){
            robot.mineralArm.setPower(-0.5);
            }else{
            robot.mineralArm.setPower(0);
            } */


    }



    public void Move(double speed)
    {
        double y = 0;
        double x =0;
        x = speed * gamepad1.left_stick_x;
        y = speed * gamepad1.left_stick_y;
        robot.leftDrive.setPower(y+x);
        robot.rightDrive.setPower(y-x);
    }
    public void Sucker()
    {
        if (gamepad1.right_bumper) {
            robot.mineralCollect.setPower(1);
        } else {
            robot.mineralCollect.setPower(0);
        }
        if (gamepad1.left_bumper) {
            robot.mineralCollect.setPower(-0.8);
        } else {
            robot.mineralCollect.setPower(0);
        }
    }
    public void liftBot()
    {
        if (gamepad2.right_bumper)
        {
            robot.liftBot.setPower(0.3);
            robot.liftBot2.setPower(0.3);
        }
        else {
            robot.liftBot2.setPower(0);
            robot.liftBot.setPower(0);
        }if (gamepad2.left_bumper)
        {
            robot.liftBot.setPower(-1);
            robot.liftBot.setPower(-1);
        }else{
            robot.liftBot.setPower(0);
            robot.liftBot2.setPower(0);
    }

    }
    public void flipper(double speed)
    {
     double y = 0;
     y = speed * gamepad2.left_stick_y;
     robot.mineralArm.setPower(y);

    }
    public void mineralCrater()
    {
        double spin = 0;
        if(Math.abs(gamepad1.right_stick_y) > 0.0)
        {
            spin = gamepad1.right_stick_y;
            robot.spin.setPower(gamepad1.right_stick_y);
        }else
            {
                robot.spin.setPower(0);
            }
        if(gamepad1.x)
        {
            robot.craterArm.setPosition(180);
        }
        if(gamepad1.y)
        {
            robot.craterArm.setPosition(0);

        }
    }

    @Override
    public void stop() {
        robot.rightDrive.setPower(0);
        robot.leftDrive.setPower(0);
    }

}
