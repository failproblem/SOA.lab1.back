<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Lab1</title>
  </head>
  <body>
  <pre>
  ==========================================================================

  POST /routes - create element

  Params:

      name        | string
      x           | float
      y           | float
      from_x      | long
      from_y      | long
      from_z      | long
      from_name   | string
      to_x        | long
      to_Y        | long
      to_z        | long
      to_name     | string

  Returns: nothing

  --------------------------------------------------------------------------

  GET /routes/{id} - get element by ID

  Params: nothing

  Returns:

      Route | Object

  --------------------------------------------------------------------------

  PUT /routes/{id} - update element by ID

  Params:

      name        | string
      x           | float
      y           | float
      from_x      | long
      from_y      | long
      from_z      | long
      from_name   | string
      to_x        | long
      to_Y        | long
      to_z        | long
      to_name     | string

  Returns: nothing

  --------------------------------------------------------------------------

  DELETE /routes/{id} - delete element by ID

  Params: nothing

  Returns: nothing

  --------------------------------------------------------------------------

  GET /routes - get array of elements. Sorting, filtering, pagination

  Params:

      sort_by     | string[] (comma-divided)
      filter_by   | string[] (comma-divided)
      page_size   | int (zero for no limit)
      page_number | int (starts from zero)

  Filter example:

  ?filter_by=name!yourname,x!3.14&sort_by=name!asc,y!desc&page_size=0

  * ASC/DESC - sort order

  Returns:

      Routes | List<Object>

  --------------------------------------------------------------------------

  GET /far-route - get element with max 'to' location

  Params: nothing

  Returns:

      Route | Object

  --------------------------------------------------------------------------

  GET /group-coordinates - get number of elements in groups by coordinates

  Params: nothing

  Returns:

      groups | Map<Coordinates, int>

  --------------------------------------------------------------------------

  GET /higher-distance - get number of elements with higher distance

  Params: nothing

      distance | long

  Return:

      number_of_elements | int

  --------------------------------------------------------------------------
  </pre>
  </body>
</html>
