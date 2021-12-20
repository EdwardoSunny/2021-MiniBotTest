package team3647.frc2021.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import team3647.lib.drivers.SparkMaxFactory;
import team3647.lib.drivers.SparkMaxFactory.Configuration;
import com.revrobotics.CANSparkMax.IdleMode;

public class Drivetrain implements PeriodicSubsystem {
    //currently has no encoders, cannot have auto
    private CANSparkMax left;
    private CANSparkMax right;
    private DifferentialDrive m_drive;
    public Drivetrain(Configuration leftConfig, Configuration rightConfig) {
        this.left = SparkMaxFactory.createSparkMax(leftConfig);
        this.right = SparkMaxFactory.createSparkMax(rightConfig);
        m_drive = new DifferentialDrive(left,right);
    }

    public void ArcadeDrive(double throttle, double turn) {
        m_drive.arcadeDrive(throttle, turn, true);
    }

    public void setBrake(){
        this.left.setIdleMode(IdleMode.kBrake);
        this.right.setIdleMode(IdleMode.kBrake);
    }

    public void setCoast() {
        this.left.setIdleMode(IdleMode.kCoast);
        this.right.setIdleMode(IdleMode.kCoast);
    }


    @Override
    public void init() {
        
    }

    @Override
    public void readPeriodicInputs() {

    }

    @Override
    public void writePeriodicOutputs() {
        
    }

    @Override
    public void end() {
        left.set(0);
        right.set(0);
    }

    @Override
    public String getName() {
        return "Drivetrain";
    }
}
