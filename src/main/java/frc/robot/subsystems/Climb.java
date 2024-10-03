// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Climb extends SubsystemBase {
  /** Creates a new Climb. */
  private static boolean climbing;
  private static boolean up;
  TalonFX motor = new TalonFX(Constants.ClimbMotorPort);
  DigitalInput limitTop = new DigitalInput(Constants.ClimbLimitTopPort);
  DigitalInput limitBot = new DigitalInput(Constants.ClimbLimitBotPort);
  public Climb() {

  }

  public void startClimb(double speed, boolean goingUp){
    if (goingUp){
      if (limitTop.get()){
        speed = 0;
      }
      up = true;
    } else {
      if (!limitBot.get()){
        speed = 0;
      }
      up = false;
    }
    climbing = true;
    setMovement(speed);
  }

  public void stopClimb(){
    setMovement(0);
  }

  public void setMovement(double speed){
    motor.setControl(new DutyCycleOut(speed));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (climbing){
      if (up){
        if (limitTop.get()){
          stopClimb();
          climbing = false;
        }
      } else {
        if (!limitBot.get()){
          stopClimb();
          climbing = false;
        }
      }
    }
  }
}
