package edu.umdearborn.astronomyapp.service;

import java.math.BigDecimal;
import java.util.List;

import edu.umdearborn.astronomyapp.entity.Module;
import edu.umdearborn.astronomyapp.entity.PageItem;
import edu.umdearborn.astronomyapp.util.json.JsonDecorator;

public interface ModuleService {

  public List<Module> getModules(String courseId, boolean showVisibleOnly);

  public Module createModule(Module module);

  public Module updateModule(Module module);

  public void deleteModule(String moduleId);

  public JsonDecorator<Module> getModuleDetails(String moduleId);

  public List<PageItem> getPage(String moduleId, int pageNumber);

  public BigDecimal getMaxPoints(String moduleId);

  public void deletePage(String moduleId, int pageNumber);

  public PageItem createPageItem(PageItem item, String moduleId, int pageNum);

  public List<PageItem> deletePageItem(String moduleId, String pageItemId);

  public int addPage(String moduleId);

  public void ensureValidDates(String courseId, Module module);

}
