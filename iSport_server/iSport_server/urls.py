from django.conf.urls import patterns, include, url
from django.contrib import admin
from iSport_server.iSport.views import register_new_user, login

admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'iSport_server.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),   # database admin
    url(r'^reg/', register_new_user),            # user register
    url(r'^login', login),                       # user login
#    url(r'^push', push),                         # user push data
)
