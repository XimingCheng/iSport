from django.db import models
from django.core.validators import MaxValueValidator, MinValueValidator

class User(models.Model):
    name = models.CharField(max_length = 100)
    password = models.CharField(max_length = 200)
    location = models.CharField(max_length = 100)
    score = models.FloatField(validators = [MinValueValidator(0.0),
        MaxValueValidator(5.0)])
    completed_id = models.CharField(max_length = 1000, blank = True)
    uncompleted_id = models.CharField(max_length = 1000, blank = True)
    sex = models.CharField(max_length = 2, default = 'F')
    user_image = models.CharField(max_length = 1024)
    label = models.CharField(max_length = 1000)


class Activity(models.Model):
    category = models.CharField(max_length = 20)
    begin_datatime = models.DateTimeField()
    end_datatime = models.DateTimeField()
    people_count = models.IntegerField(default = 0)
    models.CharField(max_length = 1000)
    location = models.CharField(max_length = 1024)
    submit_peopleId = models.IntegerField(default = 0)
    name = models.CharField(max_length = 1024)
    details = models.CharField(max_length = 3000)
