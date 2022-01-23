package com.blogspot.ostas.apps.dbdive.service.template;

import com.blogspot.ostas.apps.dbdive.model.DbColumn;
import com.blogspot.ostas.apps.dbdive.model.DbTable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.capitalize;

@Component
@RequiredArgsConstructor
public class SqlGenService {

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

	@Data
	public static class ColumnDescription {

		private String name;

		private String javaType;

		public ColumnDescription(DbColumn dbColumn) {
			this.name = dbColumn.getName();
			this.javaType = dbColumn.getJavaType().getSimpleName();
		}

	}

	public String generateJavaClassForTable(DbTable dbTable, String packageName) {
		var placeholders = new HashMap<String, Object>();
		placeholders.put("packageName", packageName);
		placeholders.put("className", capitalize(dbTable.getName()));

		placeholders.put("columns",
				dbTable.getColumns().stream().map(ColumnDescription::new).collect(Collectors.toList()));

		var nonJavaLangImports = new StringBuffer();
		var typesToBeImported = dbTable.getColumns().stream().map(DbColumn::getJavaType)
				.filter(javaType -> !javaType.getPackageName().startsWith("java.lang")).collect(Collectors.toList());
		typesToBeImported
				.forEach(type -> nonJavaLangImports.append(String.format("import %s;%n", type.getCanonicalName())));
		placeholders.put("nonJavaLangImports", nonJavaLangImports);

		return this.templateService.populateTemplate("class-template.vm", placeholders);
	}

}
