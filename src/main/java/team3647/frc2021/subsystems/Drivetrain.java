package team3647.frc2021.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpiutil.math.MathUtil;
import team3647.lib.DriveSignal;
import team3647.lib.drivers.VictorSPXFactory;
import team3647.lib.drivers.VictorSPXFactory.Configuration;
import team3647.lib.wpi.HALMethods;

public class Drivetrain implements PeriodicSubsystem {
    private VictorSPX left;
    private VictorSPX right;
    private double m_maxOutput;
    private PeriodicIO periodicIO = new PeriodicIO();
    private ControlMode controlMode = ControlMode.PercentOutput;

    private static class PeriodicIO {
        public double leftOutput;
        public double rightOutput;
    }

    public Drivetrain(int leftPin, int rightPin) {
        this.left = new VictorSPX(leftPin);
        this.right = new VictorSPX(rightPin);
        this.right.setInverted(true);
    }

    public void ArcadeDrive(double xSpeed, double zRotation, boolean scaleInputs) {
        xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
        xSpeed = applyDeadband(xSpeed, .09);

        zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);
        zRotation = applyDeadband(zRotation, .09);
        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

        if (xSpeed >= 0.0) {
            // First quadrant, else second quadrant
            if (zRotation >= 0.0) {
                leftMotorOutput = maxInput;
                rightMotorOutput = xSpeed - zRotation;
            } else {
                leftMotorOutput = xSpeed + zRotation;
                rightMotorOutput = maxInput;
            }
        } else {
            // Third quadrant, else fourth quadrant
            if (zRotation >= 0.0) {
                leftMotorOutput = xSpeed + zRotation;
                rightMotorOutput = maxInput;
            } else {
                leftMotorOutput = maxInput;
                rightMotorOutput = xSpeed - zRotation;
            }
        }
        if (scaleInputs) {
            m_maxOutput = .7;
            leftMotorOutput *= m_maxOutput;
            rightMotorOutput *= m_maxOutput;
        }

        setOpenLoop(new DriveSignal(leftMotorOutput, rightMotorOutput));
    }

    protected double applyDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }

    public synchronized void setOpenLoop(DriveSignal driveSignal) {
        if (driveSignal != null) {
            periodicIO.leftOutput = driveSignal.getLeft();
            periodicIO.rightOutput = driveSignal.getRight();
            controlMode = ControlMode.PercentOutput;
        } else {
            end();
            HALMethods.sendDSError("DriveSignal in setOpenLoop was null");
        }
    }

    public void setBrake() {
        this.left.setNeutralMode(NeutralMode.Brake);
        this.right.setNeutralMode(NeutralMode.Brake);
    }

    public void setCoast() {
        this.left.setNeutralMode(NeutralMode.Coast);
        this.right.setNeutralMode(NeutralMode.Coast);
    }


    @Override
    public void init() {
        setBrake();
    }

    @Override
    public void readPeriodicInputs() {

    }

    @Override
    public void writePeriodicOutputs() {
        left.set(controlMode, periodicIO.leftOutput);
        right.set(controlMode, periodicIO.rightOutput);
    }

    @Override
    public void end() {
        setOpenLoop(new DriveSignal(0, 0));
    }

    @Override
    public String getName() {
        return "Drivetrain";
    }
}
