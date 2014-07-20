from django import forms

class PhotoForm(forms.Form):
    user_name = forms.CharField(max_length=100)
    title     = forms.CharField(max_length=50)
    photo     = forms.ImageField()
    # we asume that user name can't be allowed to be dulplicate

