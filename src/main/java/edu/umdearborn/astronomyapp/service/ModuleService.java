package edu.umdearborn.astronomyapp.service;

import java.util.List;

import edu.umdearborn.astronomyapp.entity.Module;
import edu.umdearborn.astronomyapp.entity.PageItem;
import edu.umdearborn.astronomyapp.util.jsondecorator.JsonDecorator;

public interface ModuleService {

  public List<Module> getModules(String courseId, boolean showVisibleOnly);
  
  public Module createModule(Module module);

  public Module updateModule(Module module);

  public JsonDecorator<Module> getModuleDetails(String moduleId);
  
  public List<PageItem> getPage(String moduleId, int pageNumber);
}
