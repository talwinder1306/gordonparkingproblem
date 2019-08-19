![Class Diagram](https://github.com/talwinder1306/gordonparkingproblem/blob/master/Class%20Diagram.jpeg)


Assumption regarding functionalities while designing
1. Parking slots closer to the exits. - The parking slots closer to the exit will be filled first

2. Parking slots at lower levels for elderly. - Only lower level slots to be given to elderly if not available throw an error

3. Convert car parking to bike parking in case of increased traffic from bikes. - If a bike is given as input, we reserve 2 parking slots (both upper and lower) specifically for bikes, upper level will hold 3 bikes and lower level will hold 2 bikes.

4. On visits from royal family to multi level vehicle parking facility, slots around the assigned slot to royal family shouldn't be allocated to any other customers. - Slots around include the slot to the left and the right both upper level and lower level and the one right above or below the selected level.

5. Optional Give incentives to car pooled vehicles. - If a vehicle is car pooled it will get some offer say 10% off the fare

6. Optional Offers and discounts to repeat customers. - If a user will get a 10% discount on all parking which are a multiple of 5, i.e. on 5th visit, 10th visit and so on..


Future scope:

* Add concurrent access For multiple requests
* Add a database support
* Add a GUI for taking the input
