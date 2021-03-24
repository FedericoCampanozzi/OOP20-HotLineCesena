package hotlinecesena.model.entities.actors.player;

import hotlinecesena.model.entities.actors.DirectionList;

public enum CommandType {

    MOVE_DIAGONAL((p, dir, ang) -> p.move(dir)),
    MOVE_NORTH((p, dir, ang) -> p.move(DirectionList.NORTH.get())),
    MOVE_SOUTH((p, dir, ang) -> p.move(DirectionList.SOUTH.get())),
    MOVE_EAST((p, dir, ang) -> p.move(DirectionList.EAST.get())),
    MOVE_WEST((p, dir, ang) -> p.move(DirectionList.WEST.get())),
    ROTATE((p, dir, ang) -> p.setAngle(ang)),
    ATTACK((p, dir, ang) -> p.attack()),
    RELOAD((p, dir, ang) -> p.reload()),
    PICK_UP((p, dir, ang) -> p.pickUp()),
    USE((p, dir, ang) -> p.useItem());
    
    private Command<Player> command;
    
    CommandType(final Command<Player> command) {
        this.command = command;
    }
    
    public Command<Player> getCommand() {
        return this.command;
    }
}
