# Android-Mvi-sample-app.
Android sample app (Model-View-Intent Architecture) written in Kotlin 

## Description
### Library Used
* Retrofit
* Dagger 2
* RxAndroid
* Android Architecture component

### Api response
Here is a sample Api response.
```json
{
  "variants": {
    "variant_groups": [
      {
        "group_id": "1",
        "name": "Crust",
        "variations": [
          {
            "name": "Thin",
            "price": 0,
            "default": 1,
            "id": "1",
            "inStock": 1
          },
          {
            "name": "Thick",
            "price": 0,
            "default": 0,
            "id": "2",
            "inStock": 1,
            "isVeg": 1
          },
          {
            "name": "Cheese burst",
            "price": 100,
            "default": 0,
            "id": "3",
            "inStock": 1,
            "isVeg": 1
          }
        ]
      },
      {
        "group_id": "2",
        "name": "Size",
        "variations": [
          {
            "name": "Small",
            "price": 0,
            "default": 1,
            "id": "10",
            "inStock": 1,
            "isVeg": 0
          },
          {
            "name": "Medium",
            "price": 100,
            "default": 0,
            "id": "11",
            "inStock": 1,
            "isVeg": 1
          },
          {
            "name": ":Large",
            "price": 200,
            "default": 0,
            "id": "12",
            "inStock": 1,
            "isVeg": 0
          }
        ]
      },
      {
        "group_id": "3",
        "name": "Sauce",
        "variations": [
          {
            "name": "Manchurian",
            "price": 20,
            "default": 0,
            "id": "20",
            "inStock": 1,
            "isVeg": 0
          },
          {
            "name": "Tomato",
            "price": 20,
            "default": 0,
            "id": "21",
            "inStock": 1,
            "isVeg": 1
          },
          {
            "name": "Mustard",
            "price": 20,
            "default": 0,
            "id": "22",
            "inStock": 1,
            "isVeg": 0
          }
        ]
      }
    ],
    "exclude_list": [
      [
        {
          "group_id": "1",
          "variation_id": "3"
        },
        {
          "group_id": "2",
          "variation_id": "10"
        }
      ],
      [
        {
          "group_id": "2",
          "variation_id": "10"
        },
        {
          "group_id": "3",
          "variation_id": "22"
        }
      ]
    ]
  }
}
```

### ToDo
Figure out a way to solve state re-rendering on each view state problem. If you have a good solution, please submit a PR.

* https://www.reddit.com/r/androiddev/comments/7mb3r1/dealing_with_redundant_state_rendering_when_using/
 



