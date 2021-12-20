package team3647.frc2021.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2021.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
    private Drivetrain dt;
    private DoubleSupplier throttle;
    private DoubleSupplier turn;
    public ArcadeDrive(Drivetrain dt, DoubleSupplier throttle, DoubleSupplier turn) {
        this.dt = dt;
        this.throttle = throttle;
        this.turn = turn;
        addRequirements(dt);
    }

    @Override
    public void initialize() {
        dt.init();
    }

    @Override
    public void execute() {
        dt.ArcadeDrive(this.throttle.getAsDouble(), this.turn.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        dt.end();
    }

}
