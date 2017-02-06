package edu.umdearborn.astronomyapp.service;

import edu.umdearborn.astronomyapp.entity.Module;
import edu.umdearborn.astronomyapp.util.jsondecorator.JsonDecorator;

public interface ModuleService {

  public Module createModule(Module module);

  public Module updateModule(Module module);

  public JsonDecorator<Module> getModuleDetails(String moduleId);
}
