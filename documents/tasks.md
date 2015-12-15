- Add validation
- Tie Sensor to property
- Add menu
- Add bootstrap
- Implement remove
- Implement update
-
th:class="${#fields.hasErrors('model')} ? form-group has-error : form-group"


sensor/update.html

<div class="form-group">
						<hr />
                    </div>

                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="btn-group btn-property-group">
                                <a th:href="@{/sensor/{id}/property/add(id=${sensor.id})}" class="btn btn-primary" role="button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New property</a>
                            </div>
                        </div>
					</div>

					<div class="form-group" th:if="${not #lists.isEmpty(sensor.properties)}">
                        <div class="col-xs-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">Properties</div>
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Type</th>
                                            <th>Value</th>
                                            <th>Unit</th>
                                            <th>Boundary</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr th:each="property,propertyStat: *{properties}">
                                            <!-- <td th:text="${propertyStat.count}"></td> -->
                                            <input type="hidden" name="id" th:field="*{properties[__${propertyStat.index}__].id}" th:id="${'id-'+propertyStat.index}" />
                                            <input type="hidden" name="propertyType.id" th:field="*{properties[__${propertyStat.index}__].propertyType.id}" th:id="${'propertyType.id-'+propertyStat.index}" />
                                            <input type="hidden" name="propertyType.name" th:field="*{properties[__${propertyStat.index}__].propertyType.name}" th:id="${'propertyType.name-'+propertyStat.index}" />
                                            <input type="hidden" name="value" th:field="*{properties[__${propertyStat.index}__].value}" th:id="${'value-'+propertyStat.index}" />
                                            <input type="hidden" name="unit" th:field="*{properties[__${propertyStat.index}__].unit}" th:id="${'unit-'+propertyStat.index}" />
                                            <input type="hidden" name="boundary" th:field="*{properties[__${propertyStat.index}__].boundary}" th:id="${'boundary-'+propertyStat.index}" />
                                            <td th:text="${property.id}"></td>
                                            <td th:text="${property.propertyType.name}"></td>
                                            <td th:text="${property.value}"></td>
                                            <td th:text="${property.unit}"></td>
                                            <td th:text="${property.boundary.name}"></td>
                                            <td><a th:href="@{/sensor/{id}/property/{propertyid}/delete(id=${sensor.id}, propertyid=${property.id})}" class="btn btn-danger" role="button"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Delete</a></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
					</div>