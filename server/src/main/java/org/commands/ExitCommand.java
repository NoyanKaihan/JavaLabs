package org.commands;

import org.exceptions.*;
public class ExitCommand extends CommandImpl {
    public ExitCommand(){
        super("exit", CommandType.NORMAL);
    }
    @Override 
    public String execute(){
        throw new ExitException();
    }
}
