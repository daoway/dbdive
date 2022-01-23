package com.blogspot.ostas.apps.dbdive.service.template;

import com.blogspot.ostas.apps.dbdive.model.DbColumn;
import com.blogspot.ostas.apps.dbdive.model.DbTable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.blogspot.ostas.apps.dbdive.utils.StringUtils.getClassNameFromTable;

@Component
@RequiredArgsConstructor
public class GeneratorService {

	private final TemplateService templateService;

	public String deleteSql(DeleteInfo deleteInfo) {
		var placeholders = new HashMap<String, Object>();
		placeholders.put("deleteInfo", deleteInfo);
		return this.templateService.populateTemplate("delete.vm", placeholders);
	}

	public String deleteAllSql(String tableName) {
		var placeholders = new HashMap<String, Object>();
		placeholders.put("tableName", tableName);
		return this.templateService.populateTemplate("delete-all.vm", placeholders);
	}

	public String removeAllDataSqlScript(List<String> tablesSequence) {
		var script = new StringBuffer();
		tablesSequence.forEach((table) -> script.append(String.format("%s;%n", this.deleteAllSql(table))));
		return script.toString();
	}

	public String generateJavaClassForTable(DbTable dbTable, String packageName) {
		var placeholders = new HashMap<String, Object>();
		placeholders.put("packageName", packageName);
		placeholders.put("className", getClassNameFromTable(dbTable.getName()));

		placeholders.put("columns",
				dbTable.getColumns().stream().map(ColumnDescription::new).collect(Collectors.toList()));

		var nonJavaLangImports = new StringBuffer();
		var typesToBeImported = dbTable.getColumns().stream().map(DbColumn::getJavaType)
				.filter((javaType) -> !javaType.getPackageName().startsWith("java.lang")).collect(Collectors.toSet());
		typesToBeImported
				.forEach((type) -> nonJavaLangImports.append(String.format("import %s;%n", type.getCanonicalName())));
		placeholders.put("nonJavaLangImports", nonJavaLangImports);

		return this.templateService.populateTemplate("class-template.vm", placeholders);
	}

	@SneakyThrows
	public String writeTablePojo(DbTable dbTable, String packageName) {
		var clazz = this.generateJavaClassForTable(dbTable, packageName);
		var packageAsPAth = ClassUtils.convertClassNameToResourcePath(packageName);
		var fileName = getClassNameFromTable(dbTable.getName()) + ".java";
		final var filePath = new File("src/main/java/" + packageAsPAth);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		var absolutePath = filePath + "/" + fileName;
		Files.write(Paths.get(absolutePath), clazz.getBytes(StandardCharsets.UTF_8));
		return absolutePath;
	}

}
